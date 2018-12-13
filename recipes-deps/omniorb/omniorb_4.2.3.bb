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

DESCRIPTION = "OmniORB High Performance ORB"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=1b422f7cda3870b9c4b040b68ba1c0fe"

DEPENDS += "omniorb-native python"
DEPENDS_class-native += "python-native"

RDEPENDS_${PN}-python += "python"
PACKAGES += "${PN}-python"
PROVIDES += "${PN}-python"

SRC_URI_BASE = "http://downloads.sourceforge.net/omniorb/omniORB-${PV}.tar.bz2;name=omniORB"
SRC_URI[omniORB.md5sum]    = "10a30bae5e1fb4563b47891c6cdf2b5c"
SRC_URI[omniORB.sha256sum] = "26412ac08ab495ce5a6a8e40961fa20b7c43f623c6c26b616d210ca32f078bca"
SRC_URI = "${SRC_URI_BASE}"
SRC_URI_class-native = "${SRC_URI_BASE}"

SRC_URI_append = "\
    file://omniORB.cfg \
    file://0001-beforeauto-cross.patch \
    file://0002-python-shebang.patch \
    file://0003-embedded-appl.patch \
"

S = "${WORKDIR}/omniORB-${PV}"

# Here we need python libraries and the softlink for the omniidlmodule, we have to disable the check for soft links.
# Alternativly, we could package this into the dev package and then pull that in but that would also get all the headers
# and idl files
INSANE_SKIP_${PN}-python += "dev-so"

FILES_${PN}-python += "${libdir}/python2.7/site-packages/_omniidlmodule.so*"
FILES_${PN}-python += "${libdir}/python2.7/site-packages/omniidl/*"
FILES_${PN}-python += "${libdir}/python2.7/site-packages/omniidl_be/*"

DIRFILES = "1"

TARGET_CC_ARCH += "${LDFLAGS}"

inherit autotools pkgconfig pythonnative

CONFFILES_${PN} += "/etc/omniORB.cfg"

do_compile () {
    export TOOLBINDIR=${STAGING_BINDIR_NATIVE}
    oe_runmake
}

do_compile_class-native () {
    oe_runmake
}

do_install () {
    # Set a variable that the Makefiles obey for install.
    autotools_do_install
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/omniORB.cfg ${D}${sysconfdir}/omniORB.cfg
}

BBCLASSEXTEND = "native"
