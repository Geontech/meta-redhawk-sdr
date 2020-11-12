
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

DESCRIPTION = "REDHAWK Domain Init.D Script"

HOMEPAGE = "http://www.geontech.com"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

DEPENDS := "redhawk"
RDEPENDS_${PN} := "redhawk"

SRC_URI = "\
    file://domain-init.d \
    file://LICENSE \
    "

S = "${WORKDIR}"

# Import the SDRROOT (and OSSIEHOME) locations
inherit redhawk-sysroot update-rc.d

# For Init.d
INITSCRIPT_NAME = "domain-${REDHAWK_DOMAIN}"
INITSCRIPT_PARAMS = "start 90 2 3 4 5 . stop 01 0 1 6 ."

FILES_${PN} = " \
    ${sysconfdir}/init.d/domain-${REDHAWK_DOMAIN} \
    "

do_install () {
    # Copy it to the destination init.d folder, renamed, and patched for the domain name
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/domain-init.d    ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    sed -i "s|SDRROOT_PATH|${SDRROOT}|g"        ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    sed -i "s|OSSIEHOME_PATH|${OSSIEHOME}|g"    ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    sed -i "s|DOMAIN_NAME|${REDHAWK_DOMAIN}|g"  ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}