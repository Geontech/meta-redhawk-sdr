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
require recipes-core/core-framework/core-framework-2.2.6.inc

DESCRIPTION = "REDHAWK Core Framework GPP"

DEPENDS = "bulkiointerfaces redhawk-native"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "1"

SRC_URI_append = "\
    file://amflags_no_test_or_config.patch \
    file://GPP_ps_e.patch \
    file://issue-37-Limits.cpp.patch \
"

S = "${WORKDIR}/git/GPP/cpp"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the same as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-device

GPP_INSTALL_DIR = "${SDRROOT}/dev/devices/GPP"
GPP_SETUP = "${GPP_INSTALL_DIR}/cpp-${PACKAGE_ARCH}/gpp_setup"

FILES_${GPP_NODE_PN} = "${GPP_SETUP}"
FILES_${PN} += "${GPP_INSTALL_DIR}"

EXTRA_OECONF += "--prefix=${SDRROOT}"

# ################################################
# End user-controlled variables to adjust the node
# ################################################
RH_GPP_MCASTNIC     ?= ""
RH_GPP_NAME         ?= "GPP"
# ################################################

# Install gpp_setup to SDRROOT
do_install_append () {
    install -m 0755 ${S}/gpp_setup ${D}${GPP_SETUP}
    mkdir -p ${D}${SDRROOT}/dev/nodes/${RH_NODE_NAME}
    touch ${D}${SDRROOT}/dev/nodes/${RH_NODE_NAME}/DeviceManager.dcd.xml
}

# Run gpp_setup on first boot to build the node using
# the target's constraints.
pkg_postinst_ontarget_${PN_NODE}() {
    source /etc/profile.d/redhawk.sh
    ${GPP_SETUP} \
        --clean \
        --domainname=${REDHAWK_DOMAIN} \
        --location=${GPP_INSTALL_DIR} \
        --sdrroot=${SDRROOT} \
        --mcastnic=${RH_GPP_MCASTNIC} \
        --nodename=${RH_NODE_NAME} \
        --gppname=${RH_GPP_NAME} \
        --processorname=${PACKAGE_ARCH} \
        --addosprops
    chown -R redhawk:redhawk ${SDRROOT}/dev/nodes/${RH_NODE_NAME}
}