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
require recipes-core/core-framework/core-framework-2.2.3.inc

DESCRIPTION = "REDHAWK Core Framework GPP"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "1"

SRC_URI_append = "\
    file://amflags_no_test_or_config.patch \
    file://configure-gpp \
    file://GPP_ps_e.patch \
    file://trust-uname-m.patch \
"

# ################################################
# End user-controlled variables to adjust the node
# ################################################
RH_GPP_MCASTNIC     ?= ""
RH_GPP_NODE_NAME    ?= "DevMgr-GPP"
RH_GPP_NAME         ?= "GPP-${PACKAGE_ARCH}"
# ################################################

S = "${WORKDIR}/git/GPP/cpp"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the same as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-device

FILES_${PN} += "${SDRROOT}/*"

EXTRA_OECONF += "--prefix=${SDRROOT}"

# Setting pymod_ossie=yes is to avoid the configure call checking for the python ossie module. This isn't ideal but it checks by running python and trying to import said module which is all cross compiled.
# We could have it run in a native build but what does that really prove then?
CACHED_CONFIGUREVARS += "ac_cv_pymod_ossie=yes"

# The GPP needs to be setup once it's running on the embedded system.
# This ensures the script is added to init.d and the XML files are linked
# to the volatile filesystem.
inherit update-rc.d
INITSCRIPT_NAME = "configure-gpp"
INITSCRIPT_PARAMS = "defaults 98"

NODE_CONFIG_SCRIPT = "gpp_setup"

do_install_append() {
    install -d ${D}/etc/init.d

    # Install and patch configure-gpp
    install -m 0755 ${WORKDIR}/configure-gpp       ${D}/etc/init.d/configure-gpp
    sed -i "s|MCASTNIC|${RH_GPP_MCASTNIC}|g"       ${D}/etc/init.d/configure-gpp
    sed -i "s|GPP_NODE_NAME|${RH_GPP_NODE_NAME}|g" ${D}/etc/init.d/configure-gpp
    sed -i "s|GPP_NAME|${RH_GPP_NAME}|g"           ${D}/etc/init.d/configure-gpp
}
