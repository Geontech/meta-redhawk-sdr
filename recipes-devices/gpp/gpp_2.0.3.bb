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

DESCRIPTION = "REDHAWK Core Framework GPP"
HOMEPAGE = "http://www.redhawksdr.org"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

# ################################################
# End user-controlled variables to adjust the node
# ################################################
RH_GPP_MCASTNIC     ?= ""
RH_GPP_NODE_NAME    ?= "DevMgr-GPP"
# ################################################

DEPENDS = "redhawk-bulkio"
RDEPENDS_${PN} = "redhawk-bulkio"

PREFERRED_VERSION_redhawk-bulkio = "2.0.3"

SRC_URI = "git://github.com/RedhawkSDR/framework-GPP.git;tag=2.0.3;protocol=git \
    file://01_Clear_AMFLAGS_GPP.patch \
    file://02_armv7l_default.patch \
    file://03_Add_Missing_Files.patch \
    file://04_GPP_spd_armv7l.patch;patchdir=${WORKDIR}/git \
    file://05_GPP_ps_e.patch \
    file://configure-gpp \
"

S = "${WORKDIR}/git/cpp"


# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the sasme as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-device

FILES_${PN} += "${SDRROOT}/*"
INSANE_SKIP_${PN} += "debug-files dev-so staticdev libdir installed-vs-shipped"

EXTRA_AUTORECONF += "-I ${OSSIEHOME_STAGED}/share/aclocal/ossie"
EXTRA_OECONF += "--prefix=${SDRROOT}"

# Setting pymod_ossie=yes is to avoid the configure call checking for the python ossie module. This isn't ideal but it checks by running python and trying to import said module which is all cross compiled. 
# We could have it run in a native build but what does that really prove then?
CACHED_CONFIGUREVARS += "ac_cv_pymod_ossie=yes"

# Dumb-down the build a bit.
CXXFLAGS += "-fpermissive"
CFLAGS += "-fpermissive"


# The GPP needs to be setup once it's running on the embedded system.
# This ensures the script is added to init.d and the XML files are linked
# to the volatile filesystem.
inherit update-rc.d
INITSCRIPT_NAME = "configure-gpp"
INITSCRIPT_PARAMS = "defaults 98"

# Install the script
do_install_append() {
    install -d ${D}/etc/init.d
    sed -i "s|MCASTNIC|${RH_GPP_MCASTNIC}|g" ${WORKDIR}/configure-gpp
    install -m 0755 ${WORKDIR}/configure-gpp ${D}/etc/init.d
    
    # Run create_node
    ${D}${SDRROOT}/dev/devices/GPP/cpp/create_node.py \
        --sdrroot="${D}${SDRROOT}" \
        --nodename="${RH_GPP_NODE_NAME}"
    
}
