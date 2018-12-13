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
require recipes-core/core-framework/core-framework.inc

DESCRIPTION = "REDHAWK Device for the USRP UHD"
DEPENDS = "bulkiointerfaces frontendinterfaces uhd"
RDEPENDS_${PN} = "bulkiointerfaces frontendinterfaces uhd"

PR = "2"

SRC_URI = "git://github.com/redhawksdr/USRP_UHD;protocol=https;tag=${PV}-${PR}"

# NOTE: This recipe requires the USRP UHD driver and hardware installed
# which is provided by the meta-sdr layer which relies on meta-ettus.

# ################################################
# End user-controlled variables to adjust the node
# ################################################
RH_USRP_UHD_TYPE      ?= "e3x0"
RH_USRP_UHD_NAME      ?= ""
RH_USRP_UHD_IP        ?= ""
RH_USRP_UHD_SERIAL    ?= ""
RH_USRP_UHD_NODE_NAME ?= "DevMgr-USRP_UHD"
# ################################################

SRC_URI_append = "\
    file://nodeconfig.patch \
"

S = "${WORKDIR}/git/cpp"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the same as autotools but our build and src locations 
# are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-device

EXTRA_OECONF += "--prefix=${SDRROOT}"

FILES_${PN} += "${SDRROOT}/*"
INSANE_SKIP_${PN} += "debug-files dev-so staticdev libdir installed-vs-shipped"

# Link nodeconfig.py and SPD into the source directory so we can patch it.
addtask link_nodeconfig before do_patch after do_unpack
do_link_nodeconfig () {
    mv ${S}/../nodeconfig.py \
       ${S}/../USRP_UHD.spd.xml \
       ${S}
    ln -sf ${S}/nodeconfig.py       ${S}/../nodeconfig.py
    ln -sf ${S}/USRP_UHD.spd.xml    ${S}/../USRP_UHD.spd.xml
}

# Install the template node
do_install_append () {
    ${D}${SDRROOT}/dev/devices/rh/USRP_UHD/nodeconfig.py \
        --sdrroot="${D}${SDRROOT}" \
        --nodename="${RH_USRP_UHD_NODE_NAME}" \
        --usrptype="${RH_USRP_UHD_TYPE}" \
        --usrpip="${RH_USRP_UHD_IP}" \
        --usrpname="${RH_USRP_UHD_IP}" \
        --usrpserial="${RH_USRP_UHD_SERIAL}"
}
