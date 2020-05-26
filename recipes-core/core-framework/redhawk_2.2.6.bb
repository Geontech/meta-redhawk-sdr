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
require core-framework-autotools.inc

DESCRIPTION = "REDHAWK Core Framework"

PR = "1"

COMPONENTHOST_PN := "rh-componenthost"
PACKAGE_BEFORE_PN += "${COMPONENTHOST_PN}"
PACKAGES_append_class-native = "${COMPONENTHOST_PN}-native"

DEPENDS += "\
    omniorbpy log4cxx xsd-native omniorb omnievents e2fsprogs apr-util apr zip \
    expat boost boost-native python util-linux \
    "
RDEPENDS_${PN} += "\
    bash python omniorbpy omniorb omnievents e2fsprogs apr-util apr zip expat boost \
    python-numpy python-threading python-subprocess python-numbers python-xml \
    python-resource util-linux procps \
    "
RDEPENDS_${PN}-python += "\
    ${PN} omniorb-python omniorbpy python-numpy python-threading \
    python-numbers python-resource python-xml python-lxml python-setuptools ${COMPONENTHOST_PN} screen"

BBCLASSEXTEND = "native"
DEPENDS_class-native += "\
    omniorbpy-native omniorb-native xsd-native log4cxx-native \
    omnievents-native e2fsprogs-native apr-util-native apr-native zip-native \
    expat-native boost-native python-native python-setuptools-native \
    util-linux-native \
    "

# Enable the "core-tests" PACKAGECONFIG option if you want to build the core framework's
# tests and have them installed as a recommended package.
# INSANE_SKIP is because QA whines about bash, /bin/env, etc. even though redhawk-python
# pulls in redhawk, which pulls in bash and /bin/env exists from other core packages. So
# here, we nudge RDEPENDS in the right direction but ignore the false-positives.
PACKAGECONFIG ??= ""
PACKAGECONFIG[core-tests] = ",--without-tests,,,${PN}-core-tests"
PACKAGE_BEFORE_PN += "${@bb.utils.contains("PACKAGECONFIG", "core-tests", "${PN}-core-tests", "", d)}"
RDEPENDS_${PN}-core-tests = "redhawk-python bulkiointerfaces frontendinterfaces burstiointerfaces"
INSANE_SKIP_${PN}-core-tests += "file-rdeps staticdev dev-so"
FILES_${PN}-core-tests = "${SDRROOT}/tests"

SRC_URI_append = "\
    file://uuid_python_package.patch \
    file://redhawk_processor.patch \
    file://Fix_Idl_prefix.patch \
    file://OSSIEHOME_global_prefix.patch \
    file://allow_idl_dir_set.patch \
    file://gcc_fix_nodebooter.patch \
    file://remove_csh_scripts.patch \
    file://ossie_cv_sdr_root_target.patch \
    file://componenthost_redhawk_processor.patch \
    file://ThreadedComponent.patch \
    file://signalling.patch \
    file://screen-debugger.patch \
"

S = "${WORKDIR}/git/redhawk/src"

EXTRA_OECONF += "\
    --with-sdr=${SDRROOT} \
    --with-expat=${STAGING_EXECPREFIXDIR} \
    --with-boost-regex=boost_regex \
    "

COMPONENTHOST_PATH = "${SDRROOT}/dom/mgr/rh/ComponentHost"
FILES_${COMPONENTHOST_PN} = "${COMPONENTHOST_PATH}"

FILES_${PN} += " \
    ${OSSIEHOME}/bin/redhawk-shminfo \
    ${OSSIEHOME}/bin/redhawk-shmclean \
    ${OSSIEHOME}/bin/nodeBooter \
    ${SDRROOT}/dom/mgr/DomainManager* \
    ${SDRROOT}/dom/domain \
    ${SDRROOT}/dom/deps \
    ${SDRROOT}/dom/components \
    ${SDRROOT}/dom/waveforms \
    ${SDRROOT}/dev \
    /etc/* \
    "

FILES_${PN}-dbg += " \
    ${SDRROOT}/dev/mgr/.debug \
    ${SDRROOT}/dom/mgr/.debug \
    ${OSSIEHOME}/bin/.debug \
"

FILES_${PN}-python += " \
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

inherit useradd
USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "-u ${REDHAWK_UID} -r -s /sbin/nologin -M -d ${SDRROOT} -c 'REDHAWK System Account' -g redhawk redhawk"
GROUPADD_PARAM_${PN} = "-g ${REDHAWK_GID} redhawk"

# Patch for lack of support in specifying an alternative to armv7l and various x86 options.
do_redhawk_processor_patch () {
    find ${S} -type f -exec sed -i "s/BB_REDHAWK_PROCESSOR/${PACKAGE_ARCH}/g" {} \;
}
do_patch[postfuncs] += "do_redhawk_processor_patch"

do_install_append () {
    # Rename ComponentHost to match the entrypoint created by the
    # componenthost SPD patch
    mv ${D}${COMPONENTHOST_PATH}/ComponentHost \
        ${D}${COMPONENTHOST_PATH}/ComponentHost-${PACKAGE_ARCH}

    # if core-tests is enabled, remove the incorrectly-installed test-generated files
    # and install the whole testing directory plus the parent Makefile (for parsing)
    if ${@bb.utils.contains("PACKAGECONFIG", "core-tests", "true", "false", d)}; then
        rm -rf ${D}${OSSIEHOME}/dom
        rm -rf ${D}${OSSIEHOME}/dev
        install -Dm 0644 ${S}/Makefile ${D}${SDRROOT}/tests/Makefile
        cp -rf ${S}/testing ${D}${SDRROOT}/tests/testing
    fi
}

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
