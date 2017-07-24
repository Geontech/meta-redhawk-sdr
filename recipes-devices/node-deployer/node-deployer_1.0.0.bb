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

DESCRIPTION = "REDHAWK Node Deployer"
HOMEPAGE = "http://www.geontech.com"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

DEPENDS := "redhawk"
RDEPENDS_${PN} := "redhawk"
PREFERRED_VERSION_redhawk = "2.0.%"

SRC_URI = "\
    file://node-init.d \
    file://node-merge.py \
    file://LICENSE \
    "

S = "${WORKDIR}"

# ##########################################
# Feel free to define this in the local.conf
# as well if you want a different node name.
# ##########################################
RH_NODE_NAME ?= "DevMgr-${MACHINE}-ALL"


# Import the SDRROOT (and OSSIEHOME) locations
inherit redhawk-sysroot

PACKAGES = "${PN}"
FILES_${PN} = " \
    ${sysconfdir}/init.d/node-${RH_NODE_NAME} \
    ${SDRROOT}/* \
    "
INSANE_SKIP_${PN} += "installed-vs-shipped"

inherit update-rc.d
INITSCRIPT_NAME = "node-${RH_NODE_NAME}"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 . stop 01 0 1 6 ."

do_install () {
    # Install the merger script
    install -d ${D}${SDRROOT}/dev/nodes
    install -m 0755 ${WORKDIR}/node-merge.py ${D}${SDRROOT}/dev/nodes/node-merge.py
        
    # "Patch" variables into the script
    sed -i "s|NODE_NAME|${RH_NODE_NAME}|g"      ${WORKDIR}/node-init.d
    sed -i "s|SDRROOT_PATH|${SDRROOT}|g"        ${WORKDIR}/node-init.d
    sed -i "s|OSSIEHOME_PATH|${OSSIEHOME}|g"    ${WORKDIR}/node-init.d
    sed -i "s|DOMAIN_NAME|REDHAWK_DEV|g"        ${WORKDIR}/node-init.d
    
    # Copy it to the destination init.d folder, renamed
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/node-init.d ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}

