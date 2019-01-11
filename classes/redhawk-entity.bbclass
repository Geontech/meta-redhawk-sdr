# This file sets OSSIEHOME and other environment variables used by autotools
inherit redhawk-oeconf redhawk-sysroot pythonnative

# Basic set of depends
DEPENDS_prepend = "redhawk redhawk-native "
RDEPENDS_${PN}_prepend = "redhawk "


do_autotools_patch () {
    # Common patches among device and component as noted by YLB.
    # These were individual patch files but were being tacked on to many recipes.
    # This is to reduce some clutter.
    sed -i 's/xmldir = $(prefix)/xmldir = $(SDR_ROOT)/g' ${S}/Makefile.am
    sed -i 's/bindir = $(prefix)/bindir = $(SDR_ROOT)/g' ${S}/Makefile.am
    sed -i 's/domdir = $(prefix)/domdir = $(SDR_ROOT)/g' ${S}/Makefile.am
    sed -i 's,${prefix}/dom/deps,${SDR_ROOT}/dom/deps,g' ${S}/configure.ac

# Needed so that when the python distutils is run it can get the system prefix.
do_install_prepend() {
  export BUILD_SYS=${BUILD_SYS}
  export HOST_SYS=${HOST_SYS}
  export STAGING_INCDIR=${STAGING_INCDIR}
  export STAGING_LIBDIR=${STAGING_LIBDIR}
  export PKG_CONFIG_PATH="${OSSIEHOME_STAGED}/lib/pkgconfig:${PKG_CONFIG_PATH}"
  export PYTHONPATH=${OSSIEHOME_STAGED}/lib/python:${PYTHONPATH}
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

