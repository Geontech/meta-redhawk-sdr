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
inherit redhawk-core-framework

DESCRIPTION = "REDHAWK Framework BurstIO Interfaces"

DEPENDS += "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"
RDEPENDS_${PN}-python = "bulkiointerfaces-python"

PR = "r2"

SRC_URI_append = "\
    file://subdir_objects.patch \
    file://fix_idldir_and_remove_cppunit.patch \
    file://burstioInterfaces_libs.patch \
"

S = "${WORKDIR}/git/redhawk-core-framework/burstioInterfaces"

EXTRA_OECONF += "\
    --disable-testing \
    --with-boost-system=boost_system \
    "

PARALLEL_MAKE = ""

# Needed so that when the python distutils is run it can get the system prefix which, 
# since it's the build system python will be /.../x86_64-linux/usr and replace it with 
# our host systems name.
# 
# STAGING_BASE: Must be the staging dir b/c of configure.ac
do_configure_prepend() {
  export BUILD_SYS=${BUILD_SYS}
  export HOST_SYS=${HOST_SYS}
  export STAGING_INCDIR=${STAGING_INCDIR}
  export STAGING_LIBDIR=${STAGING_LIBDIR}
  export STAGING_BASE=${STAGING_DIR_TARGET}
  export PKG_CONFIG_PATH="${OSSIEHOME_STAGED}/lib/pkgconfig:${PKG_CONFIG_PATH}"
}

do_compile_prepend(){
  export INTERFACES_LIBDIR="${OSSIEHOME_STAGED}/lib"
}
