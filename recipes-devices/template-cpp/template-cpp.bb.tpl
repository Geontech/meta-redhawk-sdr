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


DESCRIPTION = "REDHAWK SDR C++ Device Recipe Template"
HOMEPAGE = "http://www.your-org.org"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

# DEPENDS  -- These are your build dependencies
# RDEPENDS -- Runtime dependencies (i.e., on the embedded system)
DEPENDS        = "redhawk-bulkio"
RDEPENDS_${PN} = "redhawk-bulkio"

# This ensures you depend on the right version
PREFERRED_VERSION_redhawk-bulkio = "2.0.%"


# If you do not specify a tag, you will need to specify the commit
# hash using a separate recipe variable.
SRC_URI = "git://<your URL>;tag=<your tag>;protocol=git \
    file://Add_Missing_Files.patch \
    "

# Revision number
PR = "r0" 

# This is the "source" directory for where to look for a Makefile, for example.
S = "${WORKDIR}/git/cpp"

# We use autotools, but brokensep since it's less problemmatic for us
# to build in ${S} rather than the usual behavior of trying to copy files
# out of the source tree into a build directory, and then build.
# Note the use of the redhawk-device class.  It provides some nice post-
# installation behaviors as well as SDRROOT and OSSIEHOME, among other things.
inherit autotools-brokensep pkgconfig pythonnative redhawk-device

# This ensures the packager collects things in the SDRROOT and ignores
# the fact we're not trying to break things up into individual packages.
FILES_${PN} += "${SDRROOT}/*"
INSANE_SKIP_${PN} += "debug-files dev-so staticdev libdir installed-vs-shipped"

# You may need to patch your Makefile.am so that OSSIEHOME isn't specified.
# This AUTORECONF flag handles adding that include back, but referencing
# the embedded filesystem's OSSIE installation.
# Also, sometimes it is sufficient to pass the SDRROOT variable to configure
# as the prefix to ensure it gets installed properly.
EXTRA_AUTORECONF += "-I ${OSSIEHOME_STAGED}/share/aclocal/ossie"
EXTRA_OECONF += "--prefix=${SDRROOT}"

# Setting pymod_ossie=yes is to avoid the configure call checking for the 
# python ossie module. This isn't ideal but it checks by running python and 
# trying to import said module which is all cross compiled. 
# We could have it run in a native build but what does that really prove then?
CACHED_CONFIGUREVARS += "ac_cv_pymod_ossie=yes"

# The compiler in Dizzy is GCC v4.8 which may cause problems because of the 
# liberties taken with the templating system in REDHAWK.  To get around this 
# issue, the meta-redhawk-sdr layer requires GCC 4.8 and you should (almost) 
# always use these flags:
CXXFLAGS += "-fpermissive"
CFLAGS += "-fpermissive"


# IF you plan on using node-deployer, your device must install its own
# node definition.  See the GPP, RTL2832U or the USRP_UHD for example 
# scripts.  See those recipes' do_install_append methods for how each
# calls its own script with command line options.  Also note the use of
# weak-set variables so that end-users can specify those values
# in their local.conf or through a .bbappend.


# IF your node-creating script is in the parent directory of ${S}, you
# may need to do this step if you need to apply a patch (see RTL2832U and
# the USRP_UHD).

# addtask link_nodeconfig before do_patch after do_unpack
# do_link_nodeconfig () {
#    mv ${S}/../nodeconfig.py ${S}
#    ln -sf ${S}/nodeconfig.py ${S}/../nodeconfig.py
# }


