# This file sets OSSIEHOME and other environment variables used by autotools
inherit redhawk-oeconf redhawk-sysroot pythonnative

# Basic set of depends
DEPENDS_prepend = "redhawk redhawk-native "
RDEPENDS_${PN}_prepend = "redhawk "

# Our dynamic do_patch tasks are sensitive to REDHAWK_PROCESSOR and the
# modifications are not repeatable if that variable changes since we're
# post-patching source without patch files.  My making unpack sensitive
# to the same variable, changes to it will unpack fresh source so the
# dynamic patches can run according to the change.
do_unpack[vardeps] += "REDHAWK_PROCESSOR"

do_autotools_patch () {
    # Common patches among device and component as noted by YLB.
    # These were individual patch files but were being tacked on to many recipes.
    # This is to reduce some clutter.
    sed -i 's/xmldir = $(prefix)/xmldir = $(SDR_ROOT)/g' ${S}/Makefile.am
    sed -i 's/bindir = $(prefix)/bindir = $(SDR_ROOT)/g' ${S}/Makefile.am
    sed -i 's/libdir = $(prefix)/libdir = $(SDR_ROOT)/g' ${S}/Makefile.am
    sed -i 's/domdir = $(prefix)/domdir = $(SDR_ROOT)/g' ${S}/Makefile.am
    sed -i 's,${prefix}/dom/deps,${SDR_ROOT}/dom/deps,g' ${S}/configure.ac

    # Patch the bindir to match what the spd patch will do to the entrypoint
    sed -i -r "s,(bindir = .+?cpp)/?,\1-${REDHAWK_PROCESSOR}/,g" ${S}/Makefile.am

    # Patch the libdir to match what the spd patch will do to the entrypoint
    sed -i -r "s,(libdir = .+?cpp)/?,\1-${REDHAWK_PROCESSOR}/,g" ${S}/Makefile.am

    # Patch the relationship to any softpkg dependencies
    sed -i -r "s/(^RH_SOFTPKG_CXX.+?\[cpp)(\])(.+$)/\1-${REDHAWK_PROCESSOR}\2\3/g" ${S}/configure.ac
}
do_patch[postfuncs] += "do_autotools_patch"

do_configure_prepend () {
    export BUILD_SYS=${BUILD_SYS}
    export HOST_SYS=${HOST_SYS}
    export STAGING_INCDIR=${STAGING_INCDIR}
    export STAGING_LIBDIR=${STAGING_LIBDIR}
    export PKG_CONFIG_PATH="${OSSIEHOME_STAGED}/lib/pkgconfig:${PKG_CONFIG_PATH}"
    export PYTHONPATH=${OSSIEHOME_STAGED}/lib/python:${PYTHONPATH}
    export PATH="${OSSIEHOME_STAGED}/bin:${PATH}"
}

NO_SPD_PATCH ?= "0"
do_spd_implementation_patch () {
    if [ ${NO_SPD_PATCH} -eq 0 ]; then
        export PYTHONPATH=${OSSIEHOME_STAGED_NATIVE}/lib/python:${PYTHONPATH}
        spd_utility -n "${REDHAWK_PROCESSOR}" "${S}/.."
    fi
}
do_spd_implementation_patch[cleandirs] += "${S}/../cpp-${REDHAWK_PROCESSOR}"
addtask spd_implementation_patch after do_compile before do_install

INSANE_SKIP_${PN} += "dev-so"
