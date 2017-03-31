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

DESCRIPTION = "OmniEvents init script"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "CLOSED"
DEPENDS += "omnievents"

SRC_URI = " file://omniEvents"

S = "${WORKDIR}"

inherit update-rc.d

# omniNames is 10, omniEvents will be 11.
INITSCRIPT_NAME = "omniEvents"
INITSCRIPT_PARAMS = "defaults 11"

do_install () {
    install -d ${D}/etc ${D}/etc/init.d 
    install -m 0755 ${WORKDIR}/omniEvents ${D}/etc/init.d/omniEvents
}

