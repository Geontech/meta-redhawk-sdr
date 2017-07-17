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

DEPENDS = ""
DEPENDS_virtclass-native = "xsd-dev"
BBCLASSEXTEND = "native"

PROVIDES += "${PN}-dev ${PN}-dev-native"

SRC_URI = " \
    git://scm.codesynthesis.com/xsd/xsd.git;protocol=git \
    "
SRCREV = "94cba986108a0e0f42295572ca42c356d59328d7"

S = "${WORKDIR}/git"

# Per http://www.codesynthesis.com/pipermail/xsde-users/2012-October/000535.html
# Boris says to get the binary for the host, then cross-compile and install libxsd...
# However libxsd is header-only, so this is really just making the executable visible
# to the host and the headers installed on the target.

do_install () {
    install -d ${D}${includedir}
    cp -r ${S}/libxsd/xsd ${D}${includedir}/xsd
}

do_install_virtclass-native () {
    install -m 0755 -D ${S}/bin/xsd ${D}${bindir}/xsdcxx
}