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
require recipes-core/core-framework/core-framework-2.2.3.inc

DESCRIPTION = "REDHAWK Core Framework GPP"

DEPENDS = "bulkiointerfaces redhawk-native"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "1"

SRC_URI_append = "\
    file://amflags_no_test_or_config.patch \
    file://GPP_ps_e.patch \
    file://set_processor_name \
"

GPP_NODE_PN := "${PN}-node"
PACKAGE_BEFORE_PN = "${GPP_NODE_PN}"
RDEPENDS_${GPP_NODE_PN} = "gpp"

S = "${WORKDIR}/git/GPP/cpp"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the same as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-device

FILES_${GPP_NODE_PN} += "${SDRROOT}/dev/nodes"
FILES_${PN} += "${SDRROOT}/dev/devices*"

EXTRA_OECONF += "--prefix=${SDRROOT}"

# ################################################
# End user-controlled variables to adjust the node
# ################################################
RH_GPP_MCASTNIC     ?= ""
RH_GPP_NODE_NAME    ?= "DevMgr-GPP"
RH_GPP_NAME         ?= "GPP"
# ################################################

do_set_processor_name_patch() {
    export PYTHONPATH=${OSSIEHOME_STAGED_NATIVE}/lib/python:${PYTHONPATH}
    ${WORKDIR}/set_processor_name -n ${PACKAGE_ARCH} ${S}/..
}
do_set_processor_name_patch[depends] += "${PN}:do_prepare_recipe_sysroot"
addtask set_processor_name_patch after do_patch before do_configure

do_install_append() {
    export PYTHONPATH=${OSSIEHOME_STAGED_NATIVE}/lib/python:${PYTHONPATH}
    ${S}/gpp_setup \
        --location=${S}/.. \
        --sdrroot=${D}${SDRROOT} \
        --mcastnic=${RH_GPP_MCASTNIC} \
        --nodename=${RH_GPP_NODE_NAME} \
        --gppname=${RH_GPP_NAME} \
        --processorname=${PACKAGE_ARCH} \
        --addosprops
}
