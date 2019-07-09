inherit autotools-brokensep pkgconfig redhawk-entity

do_autotools_patch_append () {
    sed -i -r "s/(^RH_SOFTPKG_PREFIX.+?\[cpp)(\])/\1-${PACKAGE_ARCH}\2/g" ${S}/configure.ac
}

# Set/append SOFTPKG_PREFIX to the installation directory of your softpkg.
# Standard REDHAWK Deps install with a package config as:
#    prefix = ${SDRROOT}/dom/deps/<location, e.g., rh/dsp, with SDRROOT expanded>
#    exec_prefix = ${prefix}/cpp
#    libdir = ${exec_prefix}/lib
#    includedir = ${prefix}/include

SOFTPKG_BASE = "${SDRROOT}/dom/deps"
SOFTPKG_PREFIX ?= "${SOFTPKG_BASE}"
SOFTPKG_INCLUDEDIR ?= "${SOFTPKG_PREFIX}/include"
SOFTPKG_EPREFIX ?= "${SOFTPKG_PREFIX}/cpp-${PACKAGE_ARCH}"
SOFTPKG_LIBDIR ?= "${SOFTPKG_EPREFIX}/lib"

EXTRA_OECONF += "\
	--prefix=${SOFTPKG_PREFIX} \
	--exec-prefix=${SOFTPKG_EPREFIX} \
	--libdir=${SOFTPKG_EPREFIX}/lib \
	--includedir=${SOFTPKG_PREFIX}/include \
	"

# Packaging
FILES_${PN} += "\
	${SOFTPKG_PREFIX}/* \
	${SOFTPKG_LIBDIR}/*.so \
	"
FILES_${PN}-dbg += "${SOFTPKG_LIBDIR}/.debug/*"
FILES_${PN}-dev += "\
	${SOFTPKG_LIBDIR}/pkgconfig \
	${SOFTPKG_INCLUDEDIR} \
	"
FILES_${PN}-staticdev += "${SOFTPKG_LIBDIR}/*.a"

# Move the xml to be at the base of SOFTPKG_PREFIX.
fakeroot do_install_append () {
	find ${D} -name "*.spd.xml" -exec mv {} ${D}${SOFTPKG_PREFIX} \;
}
