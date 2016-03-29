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

DESCRIPTION = "REDHAWK Core Framework"
HOMEPAGE = "http://www.redhawksdr.org"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the sasme as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-sysroot redhawk-oeconf

DEPENDS = "omniorbpy omniorbpy-native log4cxx xsd-dev omniorb omnievents e2fsprogs apr-util apr zip expat boost boost-native python-numpy python-threading python-numbers python-resource ossp-uuid"
RDEPENDS_${PN} = "python omniorbpy omniorb omnievents e2fsprogs apr-util apr zip expat boost python-numpy python-threading python-subprocess python-numbers python-xml python-resource ossp-uuid"

PROVIDES += "${PN}-dev ${PN}-staticdev"

SRC_URI = "\
    git://github.com/redhawksdr/framework-core.git;tag=2.0.0;protocol=git \
    file://OSSIEHOME_global_prefix.patch \
    file://Remove_Tests.patch \
    file://Add_Missing_Files.patch \
    file://Remove_x86_DomMgr.patch \
    file://Fix_Idl_prefix.patch \
    file://arm_based_device_manager.patch \
    file://remove_csh_scripts.patch \
    file://allow_idl_dir_set.patch \
"

# The tag is enough to get the right framework-core
PR = "r0" 

S = "${WORKDIR}/git/src"

EXTRA_OECONF += "\
    --disable-java \
    --with-expat=${STAGING_DIR_TARGET}/usr \
    idldir=${STAGING_DIR_TARGET}/usr/share/idl/omniORB \
    OMNICOS_IDLDIR=${STAGING_DIR_TARGET}/usr/share/idl/omniORB/COS\
    "
    
FILES_${PN} += " \
    /etc/* \
    ${OSSIEHOME}/* \
    ${SDRROOT}/* \
"

# The skip is because we're bundling up all of OSSIEHOME which contains libraries, etc.
# into a single package and none of those files are in /usr/lib, /usr/bin, etc. resulting
# in QA getting upset.  We *need* this because otherwise QA will throw an error and kill
# the packaging, and yet the downstream dependencies and post-install behaviors are hard-
# coded to look in $OSSIEHOME for these things.  o_o  
# Now enjoy some Kool Aid with the rest of us.
INSANE_SKIP_${PN} += "dev-so la libdir debug-files staticdev installed-vs-shipped"

# Dumb-down the build a bit.
CXXFLAGS += "-fpermissive"

# Needed so that when the python distutils is run it can get the system prefix.
do_install_prepend() {
    export BUILD_SYS=${BUILD_SYS}
    export HOST_SYS=${HOST_SYS}
    export STAGING_INCDIR=${STAGING_INCDIR}
    export STAGING_LIBDIR=${STAGING_LIBDIR}
}

# Because we're using non-standard locations, we have to describe our locations
# to autotools
# Get the things from /etc (sysconfdir)
redhawk_core_etc_sysroot () {
    sysroot_stage_dir ${D}${sysconfdir}/bash_completion.d \ 
        ${SYSROOT_DESTDIR}${sysconfdir}/bash_completion.d
    sysroot_stage_dir ${D}${sysconfdir}/ld.so.conf.d \
        ${SYSROOT_DESTDIR}${sysconfdir}/ld.so.conf.d
    sysroot_stage_dir ${D}${sysconfdir}/profile.d \
        ${SYSROOT_DESTDIR}${sysconfdir}/profile.d
}

SYSROOT_PREPROCESS_FUNCS += "redhawk_core_etc_sysroot"
    
