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

DESCRIPTION = "OmniEvents"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=68ad62c64cc6c620126241fd429e68fe"
DEPENDS += "omniorb boost"
PR = "r0"

# FIXME: Once they finish using develop as the main branch, point this and SRCREV
#        at the suitable version.  (i.e., whenever they formally release 2.7 *as* 2.7)
SRC_URI = "git://github.com/redhawksdr/omniEvents;branch=develop;name=omniEvents270git \
    file://omniEvents.patch \
    file://config.mk.patch \
    file://omniEvents \
"

SRCREV = "5c1850d806bb5412a30f422e394dad364a53a533"

S = "${WORKDIR}/git"

inherit autotools pkgconfig update-rc.d

EXTRA_OECONF="\
    --with-omniorb=${STAGING_DIR}/${MACHINE}/usr \
    --with-boost=${STAGING_DIR}/${MACHINE}/usr \
    "
    
# Over-write default multi-threaded build temporarily.
PARALLEL_MAKE = ""

# omniNames is 10, omniEvents will be 11.
INITSCRIPT_NAME = "omniEvents"
INITSCRIPT_PARAMS = "defaults 11"

do_configure_append () {
    # omniEvents isn't quite as auto-tooled as omniNames so its build
    # in OE is a bit more involved; we have to manually copy sources, make files, etc.
    # At this point, we're in the build folder.
    cp -r ../git/Makefile ../git/idl ../git/src ../git/contrib ../git/tools ../git/examples .
}

do_compile () {
	export IDL=${STAGING_DIR}/${BUILD_SYS}/usr/bin/omniidl
	export IDL_COS_DIR=${STAGING_DIR}/${BUILD_SYS}/usr/share/idl/omniORB
    oe_runmake
}

do_install () {
    # Set a variable that the Makefiles obey for install.
    autotools_do_install
    install -d ${D}/etc ${D}/etc/init.d 
    install -m 0755 ${WORKDIR}/omniEvents ${D}/etc/init.d/omniEvents
}

