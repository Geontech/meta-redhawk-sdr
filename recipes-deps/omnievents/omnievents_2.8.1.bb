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

BBCLASSEXTEND = "native"
DEPENDS += "omniorb omniorb-native boost"
DEPENDS_class-native += "omniorb-native boost-native"

PR = "r1"

SRC_URI = "git://github.com/redhawksdr/omniEvents.git;branch=develop;tag=2.8.1;protocol=git \
    file://config.mk.patch \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF="\
    --with-omniorb=${STAGING_EXECPREFIXDIR} \
    --with-boost=${STAGING_EXECPREFIXDIR} \
    "
    
# Over-write default multi-threaded build temporarily.
PARALLEL_MAKE = ""

do_configure_append () {
    # omniEvents isn't quite as auto-tooled as omniNames so its build
    # in OE is a bit more involved; we have to manually copy sources, make files, etc.
    # At this point, we're in the build folder.
    cp -r ../git/Makefile ../git/idl ../git/src ../git/contrib ../git/tools ../git/examples .
}

do_compile () {
	export IDL=${STAGING_BINDIR_NATIVE}/omniidl
	export IDL_COS_DIR=${STAGING_DATADIR}/idl/omniORB
    oe_runmake
}

do_install () {
    # Set a variable that the Makefiles obey for install.
    autotools_do_install
}
