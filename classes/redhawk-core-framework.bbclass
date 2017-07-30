# All based on the same source repository.
require recipes-core/include/redhawk-repo.inc

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the sasme as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-oeconf redhawk-sysroot

# For parsing IDL during compile and autoconf detect omniORB IDL
DEPENDS += "omniorbpy-native"

# Each typically provides a python package
PACKAGES += "${PN}-python"
PROVIDES += "${PN}-python"

# Common packaging structure
FILES_${PN}-python += " \
    ${OSSIEHOME}/lib/python \
"

FILES_${PN} += " \
    ${OSSIEHOME}/share \
    ${OSSIEHOME}/lib/lib*.so.* \
"

FILES_${PN}-dbg += " \
    ${OSSIEHOME}/lib/.debug \
"

FILES_${PN}-dev += " \
    ${OSSIEHOME}/lib/*.so \
    ${OSSIEHOME}/include \
    ${OSSIEHOME}/lib/pkgconfig \
"

FILES_${PN}-staticdev += " \
    ${OSSIEHOME}/lib/*.a \
    ${OSSIEHOME}/lib/*.la \
"

# Skips for non-standard libdir location ($OSSIEHOME -> /usr/redhawk-sdr/[lib, share, etc.])
#                                                            ^^^^^^^^^^^
INSANE_SKIP_${PN} += "libdir"
INSANE_SKIP_${PN}-dev += "libdir"
INSANE_SKIP_${PN}-dbg += "libdir"

# OECONF
EXTRA_OECONF += "\
	--disable-java \
    idldir=${STAGING_DATADIR}/idl/omniORB \
    OMNICOS_IDLDIR=${STAGING_DATADIR}/idl/omniORB/COS \
"

# Needed so that when the python distutils is run it can get the system prefix.
do_install_prepend() {
  export BUILD_SYS=${BUILD_SYS}
  export HOST_SYS=${HOST_SYS}
  export STAGING_INCDIR=${STAGING_INCDIR}
  export STAGING_LIBDIR=${STAGING_LIBDIR}
}

