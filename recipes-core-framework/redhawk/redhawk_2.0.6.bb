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

include recipes-core/include/redhawk-repo.inc

PR = "r2"

DEPENDS = "uname-machine omniorbpy omniorbpy-native log4cxx xsd-native omniorb omnievents e2fsprogs apr-util apr zip expat boost boost-native python-numpy python-threading python-numbers python-resource ossp-uuid"
RDEPENDS_${PN} = "python omniorbpy omniorb omnievents e2fsprogs apr-util apr zip expat boost python-numpy python-threading python-subprocess python-numbers python-xml python-resource ossp-uuid"
RDEPENDS_${PN}-python = "${PN} omniorb-python omniorbpy python-numpy python-threading python-numbers python-resource python-xml python-lxml"

PACKAGES += "${PN}-python"
PROVIDES += "${PN}-python"

PREFERRED_VERSION_omniorb = "4.2.0"

SRC_URI_append = "\
    file://uuid_python_package.patch \
    file://uname_machine.patch \
    file://Fix_Idl_prefix.patch \
    file://OSSIEHOME_global_prefix.patch \
    file://Remove_Tests.patch \
    file://allow_idl_dir_set.patch \
    file://gcc_fix_applicationSupport.patch \
    file://gcc_fix_nodebooter.patch \
    file://remove_csh_scripts.patch \
    file://ossie_cv_sdr_root_target.patch \
"

S = "${WORKDIR}/git/redhawk-core-framework/redhawk/src"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the sasme as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-oeconf redhawk-sysroot

EXTRA_OECONF += "\
    --with-sdr=${SDRROOT} \
    --disable-java \
    --with-expat=${STAGING_EXECPREFIXDIR} \
    idldir=${STAGING_DATADIR}/idl/omniORB \
    OMNICOS_IDLDIR=${STAGING_DATADIR}/idl/omniORB/COS \
    --with-boost-regex=boost_regex \
    --disable-log4cxx \
    "

CXXFLAGS += "-fpermissive"

FILES_${PN}-dbg += " \
    ${SDRROOT}/dev/mgr/.debug \
    ${SDRROOT}/dom/mgr/.debug \
    ${OSSIEHOME}/lib/.debug \
    ${OSSIEHOME}/bin/.debug \
"

FILES_${PN}-python += " \
    ${OSSIEHOME}/lib/python \
    ${OSSIEHOME}/bin/sdrlint \
    ${OSSIEHOME}/bin/prf2py.py \
    ${OSSIEHOME}/bin/cleanns \
    ${OSSIEHOME}/bin/redhawk-softpkg \
    ${OSSIEHOME}/bin/rh_net_diag \
    ${OSSIEHOME}/bin/rhlauncher \
    ${OSSIEHOME}/bin/nodeCleanup.py \
    ${OSSIEHOME}/bin/cleanomni \
    ${OSSIEHOME}/bin/scaclt \
    ${OSSIEHOME}/bin/qtbrowse \
    ${OSSIEHOME}/bin/py2prf \
    ${OSSIEHOME}/bin/eventviewer \
    ${OSSIEHOME}/bin/cleanes \
"

FILES_${PN} += " \
    ${OSSIEHOME}/lib/lib*.so.* \
    ${OSSIEHOME}/bin/nodeBooter \
    ${OSSIEHOME}/share \
    /etc/* \
    ${SDRROOT}/* \
"

FILES_${PN}-staticdev += " \
    ${OSSIEHOME}/lib/lib*.a \
    ${OSSIEHOME}/lib/lib*.la \
"

FILES_${PN}-dev += " \
    ${OSSIEHOME}/lib/lib*.so \
    ${OSSIEHOME}/include \
    ${OSSIEHOME}/lib/pkgconfig \
"

INSANE_SKIP_${PN} += "libdir"
INSANE_SKIP_${PN}-dev += "libdir"
INSANE_SKIP_${PN}-dbg += "libdir"

do_package_arch_patch () {
    UNAME_MACHINE=`cat ${OSSIEHOME_STAGED}/share/uname_machine`
    find ${S} -type f -exec sed -i "s/BB_UNAME_MACHINE/${UNAME_MACHINE}/g" {} \;
}
addtask package_arch_patch after do_patch before do_configure
do_package_arch_patch[depends] += "uname-machine:do_populate_sysroot"

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
