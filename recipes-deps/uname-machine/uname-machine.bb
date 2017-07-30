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

# The purpose of this recipe is to fetch the UTS_MACHINE from the kernel configuration 
# and store it in the staged OSSIEHOME/shared location as a txt file containing the name.  
# Other classes and/or recipes can then use this value for patching architecture values
# into REDHAWK's XML.
#
# The stored location is OSSIEHOME/shared/uname_machine
# 

DESCRIPTION = "UNAME Machine Check"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.geontech.com"

PR = "r0"

DEPENDS = "virtual/kernel"
RDEPENDS_${PN} = ""

# This provides the staging paths and the task to deploy the file.
inherit redhawk-sysroot

UTS_MACHINE_DEF_H = "include/generated/compile.h"
UTS_MACHINE_PATH = "${STAGING_KERNEL_BUILDDIR}/${UTS_MACHINE_DEF_H}"

UNAME_MACHINE_FILE = "uname_machine"
UNAME_MACHINE_DIR = "${OSSIEHOME}/share"
UNAME_MACHINE_PATH = "${UNAME_MACHINE_DIR}/${UNAME_MACHINE_FILE}"

FILES_${PN} += "${UNAME_MACHINE_PATH}"

do_compile () {
    sed -n "s/.*UTS_MACHINE.*\"\(.*\)\"/\1/p" "${UTS_MACHINE_PATH}" > "${S}/${UNAME_MACHINE_FILE}"
}

do_install () {
    install -d ${D}/${UNAME_MACHINE_DIR}
    install -m 0644 ${S}/${UNAME_MACHINE_FILE} ${D}/${UNAME_MACHINE_PATH}
}
