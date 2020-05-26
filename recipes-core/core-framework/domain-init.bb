
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
    file://domain-systemd \
    file://redhawk-domain-log.cfg \
    file://LICENSE \
    "

S = "${WORKDIR}"

# Import the SDRROOT (and OSSIEHOME) locations
inherit redhawk-sysroot

RH_DOMAIN_NAME ?= "REDHAWK_DEV"
RH_LOG_CFG_PATH = "${sysconfdir}/redhawk-domain-log.cfg"

# For sysvinit
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "redhawk-domain"
INITSCRIPT_PARAMS_${PN} = "start 90 2 3 4 5 . stop 01 0 1 6 ."

# For systemd
inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "redhawk-domain.service"
SYSTEMD_AUTO_ENABLE = "enable"

FILES_${PN} = "\
    ${sysconfdir}/init.d/redhawk-domain \
    ${systemd_system_unitdir}/redhawk-domain.service \
    ${sysconfdir}/redhawk-domain-log.cfg \
    "

do_compile () {
    SOURCE_FILE=""
    TARGET_FILE=""
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        SOURCE_FILE="domain-init.d"
        TARGET_FILE="redhawk-domain"
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        SOURCE_FILE="domain-systemd"
        TARGET_FILE="redhawk-domain.service"
    fi
    cp -f ${S}/${SOURCE_FILE} ${B}/${TARGET_FILE}
    sed -i "s|SDRROOT_PATH|${SDRROOT}|g"         ${B}/${TARGET_FILE}
    sed -i "s|OSSIEHOME_PATH|${OSSIEHOME}|g"     ${B}/${TARGET_FILE}
    sed -i "s|DOMAIN_NAME|${RH_DOMAIN_NAME}|g"   ${B}/${TARGET_FILE}
    sed -i "s|LOG_CFG_PATH|${RH_LOG_CFG_PATH}|g" ${B}/${TARGET_FILE}
}

do_install () {
    install -Dm 0644 ${S}/redhawk-domain-log.cfg ${D}${RH_LOG_CFG_PATH}

    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        install -Dm 0755 ${B}/redhawk-domain ${D}${sysconfdir}/init.d/redhawk-domain
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -Dm 0644 ${B}/redhawk-domain.service ${D}${systemd_system_unitdir}/redhawk-domain.service
    fi
}