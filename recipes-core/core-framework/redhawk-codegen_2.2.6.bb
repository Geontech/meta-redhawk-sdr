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
require core-framework-2.2.6.inc
inherit setuptools redhawk-sysroot

DESCRIPTION = "REDHAWK Codegen"

DEPENDS += "redhawk"
RDEPENDS_${PN} = "redhawk python"

PR = "1"

S = "${WORKDIR}/git/redhawk-codegen"

do_configure[noexec] = "1"

do_install_append() {
    rm -f ${D}${OSSIEHOME}/lib/python/redhawk/__init__.py*
}

BBCLASSEXTEND = "native"

PYTHON_SITEPACKAGES_DIR = "${OSSIEHOME}/lib/python"
DISTUTILS_INSTALL_ARGS = "--prefix=${D}/${OSSIEHOME}"

FILES_${PN} = "${OSSIEHOME}"
