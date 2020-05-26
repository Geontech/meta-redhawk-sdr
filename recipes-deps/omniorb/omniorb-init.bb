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

DESCRIPTION = "OmniORB High Performance ORB (init script)"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "CLOSED"

DEPENDS += "omniorb"
RDEPENDS_${PN} = "omniorb"

SRC_URI = "\
    file://omniNames \
    file://omniNames.service \
"

S = "${WORKDIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "omniNames"
INITSCRIPT_PARAMS_${PN} = "defaults 10"

inherit systemd
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "omniNames.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install () {
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        install -Dm 0755 ${WORKDIR}/omniNames ${D}${sysconfdir}/init.d/omniNames
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -Dm 0644 ${WORKDIR}/omniNames.service ${D}${systemd_system_unitdir}/omniNames.service
    fi
}
