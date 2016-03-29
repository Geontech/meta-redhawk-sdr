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

DESCRIPTION = "XSD Libraries for XML parsing"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=79e31466c4d9f3a85f2f987c11ebcd83"

DEPENDS += "xsd-native"
DEPENDS_virtclass-native = ""
BBCLASSEXTEND = "native"

PROVIDES += "${PN}-dev"

SRC_URI = " \
    http://codesynthesis.com/download/xsd/3.3/linux-gnu/x86_64/xsd-3.3.0-x86_64-linux-gnu.tar.bz2;name=xsd330bintarbz2 \
    "

S = "${WORKDIR}/xsd-3.3.0-x86_64-linux-gnu"

SRC_URI[xsd330bintarbz2.md5sum] = "62cf1291e210eba37738d5a02f7d2990"
SRC_URI[xsd330bintarbz2.sha256sum] = "e964e09394c06a3e50a2ef6bd318aac45018ee1d7cd2955170f70426ef20f716"


# Per http://www.codesynthesis.com/pipermail/xsde-users/2012-October/000535.html
# Boris says to get the binary for the host, then cross-compile and install libxsd...
# However libxsd is header-only, so this is really just making the executable visible
# to the host and the headers installed on the target.

do_install () {
    install -d ${D}${includedir}
    cp -r ${S}/libxsd/xsd ${D}${includedir}/xsd
}

# Renaming xsd to xsdcxx since that's another common way to spell it.
do_install_virtclass-native () {
    install -m 0755 -D ${S}/bin/xsd ${D}${bindir_cross}/xsdcxx
}

