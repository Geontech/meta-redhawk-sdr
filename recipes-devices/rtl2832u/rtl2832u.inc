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

DESCRIPTION = "REDHAWK Device for the RTL2832U"
HOMEPAGE = "http://www.redhawksdr.org"
LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=92aadbd9e4b26926809a4e97460613d5"

# ################################################
# End user-controlled variables to adjust the node
# ################################################
RH_RTL2832U_NAME      ?= ""
RH_RTL2832U_VENDOR    ?= ""
RH_RTL2832U_PRODUCT   ?= ""
RH_RTL2832U_SERIAL    ?= ""
RH_RTL2832U_INDEX     ?= ""
RH_RTL2832U_NODE_NAME ?= "DevMgr-RTL2832U"
# ################################################


DEPENDS = "redhawk-frontend redhawk-bulkio rtlsdr"
RDEPENDS_${PN} = "redhawk-frontend rtlsdr"

# Sources, by default allow for the use of SRCREV pointing to orphaned tags/commits
SRCTAG ?= ""
SRCTAGARG = "${@['nobranch=1', 'tag=${SRCTAG}'][d.getVar('SRCTAG', True) != '']}"

SRC_URI = "git://github.com/RedhawkSDR/RTL2832U.git;${SRCTAGARG};protocol=git \
    file://Add_Missing_Files.patch \
    file://Fix_rtl_version_constraint.patch \
    file://Clear_AMFLAGS.patch \
    file://nodeconfig_${PV}.patch \
"

S = "${WORKDIR}/git/cpp"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the same as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-device

FILES_${PN} += "${SDRROOT}/*"
INSANE_SKIP_${PN} += "debug-files dev-so staticdev libdir installed-vs-shipped"

EXTRA_OECONF += "--prefix=${SDRROOT}"
EXTRA_AUTORECONF += "-I ${OSSIEHOME_STAGED}/share/aclocal/ossie"

# Link nodeconfig.py into the source directory so we can patch it.
addtask link_nodeconfig before do_patch after do_unpack
do_link_nodeconfig () {
    mv ${S}/../nodeconfig.py ${S}
    ln -sf ${S}/nodeconfig.py ${S}/../nodeconfig.py
}

# Install the template node
do_install_append () {
    ${D}${SDRROOT}/dev/devices/rh/RTL2832U/nodeconfig.py \
        --sdrroot="${D}${SDRROOT}" \
        --nodename="DevMgr-${MACHINE}-RTL2832U" \
        --rtlname="${RH_RTL2832U_NAME}" \
        --rtlvendor="${RH_RTL2832U_VENDOR}" \
        --rtlproduct="${RH_RTL2832U_PRODUCT}" \
        --rtlserial="${RH_RTL2832U_SERIAL}" \
        --rtlindex="${RH_RTL2832U_INDEX}" \
        --inplace
}

