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
require core-framework-2.2.2.inc
inherit setuptools redhawk-sysroot  

DESCRIPTION = "REDHAWK Codegen"

DEPENDS += "redhawk python-setuptools "
RDEPENDS_${PN} = "redhawk python"

PR = "1"

S = "${WORKDIR}/git/redhawk-codegen"

# Recipe concept based on: http://stackoverflow.com/questions/16090550/building-python-packages

# This is a python package

do_configure_prepend() {
    export BUILD_SYS=${BUILD_SYS}
    export HOST_SYS=${HOST_SYS}
    export STAGING_INCDIR=${STAGING_INCDIR}
    export STAGING_LIBDIR=${STAGING_LIBDIR}
}

do_install_append() {
    rm -f ${D}${OSSIEHOME}/lib/python/redhawk/__init__.py*
}

BBCLASSEXTEND = "native"

PYTHON_SITEPACKAGES_DIR = "${OSSIEHOME}/lib/python"
DISTUTILS_INSTALL_ARGS = "--prefix=${D}/${OSSIEHOME}"

FILES_${PN} = "${OSSIEHOME}"
