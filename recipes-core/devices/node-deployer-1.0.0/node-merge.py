#!/usr/bin/env python
#
# This file is protected by Copyright. Please refer to the COPYRIGHT file distributed 
# with this source distribution.
#
# This file is part of Geon Technology's meta-redhawk-sdr.
#
# Geon Technology's meta-redhawk-sdr is free software: you can redistribute it and/or 
# modify it under the terms of the GNU Lesser General Public License as published by 
# the Free Software Foundation, either version 3 of the License, or (at your option) 
# any later version.
#
# Geon Technology's meta-redhawk-sdr is distributed in the hope that it will be useful, 
# but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
# details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see http://www.gnu.org/licenses/.
#


import os, sys, commands, logging, shutil, socket
from ossie import parsers
from ossie.utils.model import _uuidgen as uuidgen

class ConfigurationError(StandardError):
    pass

class NodeMerge(object):
    def __init__(self, options, cmdlineProps):
        # Basic setup
        self._log = logging.getLogger('NodeMerge')
        self.localfile_nodeprefix = '/mgr'
        self.options = options
        self.cmdlineProps = cmdlineProps
        self.hostname = socket.gethostname()
        
        # check domainname
        if options.domainname == None:
            raise ConfigurationError("A domainname is required")
        
        # Common node path
        self.node_base_path = os.path.join(self.options.sdrroot, "dev", "nodes")
        
        # This node
        self.nodedir = os.path.join(self.node_base_path, self.options.nodename.replace('.','/'))
        self.path_to_dcd = os.path.join(self.nodedir , "DeviceManager.dcd.xml")
        
        # Find all paths for dcd.xml's other than this node.
        def find_dcds(path):
            dcds = []
            for root, dirs, files in os.walk(path):
                for name in files:
                    if ".dcd.xml" in name:
                        test_path = os.path.join(root, name)
                        if test_path != self.path_to_dcd:
                            dcds.append(parsers.DCDParser.parse(test_path))
            return dcds
        self.other_dcds = find_dcds(self.node_base_path)
        
        if not self.other_dcds:
            raise ConfigurationError("No nodes found to merge.")  
             
        # prep uuids
        self.uuids = {}
        self.uuids["deviceconfiguration"    ] = 'DCE:' + uuidgen()

    def register(self):
        if not self.options.silent:
            self._log.debug("Registering...")
        self._createDeviceManagerProfile()
    
    def unregister(self):
        if not self.options.silent:
            self._log.debug("Unregistering...")
        if os.path.isdir(self.nodedir):
            if not self.options.silent:
                self._log.debug("  Removing <" + self.nodedir + ">")
            shutil.rmtree(self.nodedir)
         
    def _ver2rel(self, ver):
        return float(ver[0:1]) + float(ver[2:3])*0.1 + float(ver[4:5])*0.000001

    def _createDeviceManagerProfile(self):
        #####################
        # Setup environment
        #####################

        # make sure node hasn't already been created
        if os.path.exists(self.path_to_dcd):
            self.unregister()

        try:
            if not os.path.isdir(self.nodedir):
                os.makedirs(self.nodedir)
            else:
                if not self.options.silent:
                    self._log.debug("Node directory already exists; skipping directory creation")
                pass
        except OSError:
            raise Exception, "Could not create device manager directory"

        #####################
        # DeviceManager files
        #####################
        if not self.options.silent:
            self._log.debug("Creating DeviceManager profile <" + self.options.nodename + ">")
        
        # set deviceconfiguration info
        _dcd = parsers.DCDParser.deviceconfiguration()
        _dcd.set_id(self.uuids["deviceconfiguration"])
        _dcd.set_name(self.options.nodename)
        _localfile = parsers.DCDParser.localfile(name=os.path.join(self.localfile_nodeprefix, 'DeviceManager.spd.xml'))
        _dcd.devicemanagersoftpkg = parsers.DCDParser.devicemanagersoftpkg(localfile=_localfile)
        
        # Add all componentfile(s)
        _dcd.componentfiles = parsers.DCDParser.componentfiles()
        for o in self.other_dcds:
            ocs = o.get_componentfiles()
            for compfile in ocs.get_componentfile():
                _dcd.componentfiles.add_componentfile(compfile)
        
        # add partitioning/componentplacements
        _dcd.partitioning = parsers.DCDParser.partitioning()
        for o in self.other_dcds:
            op = o.get_partitioning()
            [_dcd.partitioning.add_componentplacement(p) for p in op.get_componentplacement()]
            
        # Add connections, filesystemnames
        other_connections = []
        other_fsnames = []
        for dcd in self.other_dcds:
            cons = dcd.get_connections()
            fsns = dcd.get_filesystemnames()
            if cons:
                for con in cons.get_connectinterface():
                    other_connections.append(con)
            if fsns:
                for ofs in fsns.get_filesystemname():
                    other_fsnames.append(ofs)
                
        if other_connections:
            _dcd.connections = parsers.DCDParser.connections()
            [_dcd.connections.add_connectinterface(oc) for oc in other_connections]
        
        if other_fsnames:
            _dcd.filesystemnames = parsers.DCDParser.filesystemnames()
            [_dcd.filesystemnames.add_filesystemname(fsn) for fsn in other_fsnames]
            

        # add domainmanager lookup
        if self.options.domainname:
            _tmpdomainname = self.options.domainname + '/' + self.options.domainname
        _dcd.domainmanager = parsers.DCDParser.domainmanager(namingservice=parsers.DCDParser.namingservice(name=_tmpdomainname))
        
        # Write out the file
        dcd_out = open(self.path_to_dcd, 'w')
        dcd_out.write(parsers.parserconfig.getVersionXML())
        _dcd.export(dcd_out,0)
        dcd_out.close()
        

        
###########################
# Run from command line
###########################
if __name__ == "__main__":

    ##################
    # setup arg parser
    ##################
    from optparse import OptionParser
    parser = OptionParser()
    #parser.usage = "%s [options] [simple_prop1 simple_value1]..."
    parser.add_option("--domainname", dest="domainname", default="REDHAWK_DEV",
                      help="Must give a domain name")
    parser.add_option("--sdrroot", dest="sdrroot", default=os.path.expandvars("${SDRROOT}"),
                      help="Path to the sdr root; if none is given, ${SDRROOT} is used.")
    parser.add_option("--nodename", dest="nodename", default="DevMgr_ALL",
                      help="Desired nodename, if none is given DevMgr_ALL is used")
    parser.add_option("--silent", dest="silent", default=False, action="store_true",
                      help="Suppress all logging except errors")
    parser.add_option("--clean", dest="clean", default=False, action="store_true",
                      help="Clean up the previous configuration for this node first (delete entire node)")
    parser.add_option("-v", "--verbose", dest="verbose", default=False, action="store_true",
                      help="Enable verbose logging")

    (options, args) = parser.parse_args()

    # Configure logging
    logging.basicConfig(format='%(name)-12s:%(levelname)-8s: %(message)s', level=logging.INFO)
    if options.verbose:
        logging.getLogger().setLevel(logging.DEBUG)

    # grab tmp logger until class is created
    _log = logging.getLogger('NodeMerge')

    if len(args) % 2 == 1:
        _log.error("Invalid command line arguments - properties must be specified with values")
        sys.exit(1)
    cmdlineProps = {}
    for i in range(len(args)):
        if i % 2 == 0:
            cmdlineProps[args[i]] = args[i + 1]

    # create instance of NodeMerge
    try:
        dn = NodeMerge(options, cmdlineProps)
        if options.clean:
            dn.unregister()
        dn.register()
        if not options.silent:
            _log.info(options.nodename + " node registration is complete")
    except ConfigurationError, e:
        _log.error("%s", e)
        sys.exit(1)
