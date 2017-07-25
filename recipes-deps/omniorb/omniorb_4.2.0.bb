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
LIC_FILES_CHKSUM = "file://COPYING;md5=75b02c2872421380bbd47781d2bd75d3"

DEPENDS += "omniorb-native python"
DEPENDS_virtclass-native += "python-native"

RDEPENDS_${PN}-python += "python"
PACKAGES += "${PN}-python"
PROVIDES += "${PN}-python"

SRC_URI_BASE = "http://downloads.sourceforge.net/omniorb/omniORB-4.2.0.tar.bz2;name=omniORB420tarbz2"
SRC_URI = "${SRC_URI_BASE}"
SRC_URI_virtclass-native = "${SRC_URI_BASE}"
SRC_URI[omniORB420tarbz2.md5sum] = "f1e104d0a2df92829c1b37a853f4805d"
SRC_URI[omniORB420tarbz2.sha256sum] = "74c273fc997c2881b128feb52182dbe067acfecc4cf37475f43c104338eba8bc"

SRC_URI_append = "\
    file://omniORB.cfg \
    file://omniORB-cross.patch \
    file://omniORB_embedded_appl.patch \
    file://pyPrefixIsPrefix.patch \
    file://fixPythonShebang.patch \
    file://rm_LongDouble.patch \
"

S = "${WORKDIR}/omniORB-${PV}"

# Here we need python libraries and the softlink for the omniidlmodule, we have to disable the check for soft links.
# Alternativly, we could packge this into the dev package and then pull that in but that would also get all the headers
# and idl files
INSANE_SKIP_${PN}-python += "dev-so"

FILES_${PN}-python += "${libdir}/python2.7/site-packages/_omniidlmodule.so*"
FILES_${PN}-python += "${libdir}/python2.7/site-packages/omniidl/*"
FILES_${PN}-python += "${libdir}/python2.7/site-packages/omniidl_be/*"
FILES_${PN}-dbg += "${libdir}/python2.7/site-packages/.debug/_omniidlmodule.so.4.1"


TARGET_CC_ARCH += "${LDFLAGS}"

inherit autotools pkgconfig pythonnative

CONFFILES_${PN} += "/etc/omniORB.cfg"

do_compile () {
    export TOOLBINDIR=${STAGING_BINDIR_NATIVE}
    oe_runmake
}

do_compile_virtclass-native () {
    oe_runmake
}

do_install () {
    # Set a variable that the Makefiles obey for install.
    autotools_do_install
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/omniORB.cfg ${D}${sysconfdir}/omniORB.cfg
}

BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"
