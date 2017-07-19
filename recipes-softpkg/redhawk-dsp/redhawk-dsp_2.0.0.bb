DESCRIPTION = "REDHAWK Core Framework GPP"

include recipes-core/include/redhawk-repo.inc

DEPENDS = "redhawk-bulkio"
RDEPENDS_${PN} = "redhawk-bulkio"
PREFERRED_VERSION_redhawk-bulkio = "2.0.6"

SRC_URI_append = " \
    file://Add_Missing_Files.patch \
    "

PR = "r1"

S = "${WORKDIR}/git/redhawk-sharedlibs/dsp/cpp"

# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the sasme as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-sysroot redhawk-entity

FILES_${PN} += "${SDRROOT}/*"
FILES_${PN}-dbg += "${SDRROOT}/dom/deps/dsp/cpp/lib/.debug/*"
FILES_${PN}-dev += "${SDRROOT}/dom/deps/dsp/cpp/lib/libdsp.so"
FILES_${PN}-staticdev += "${SDRROOT}/dom/deps/dsp/cpp/lib/libdsp.a"

# Soft packages have an additional aclocal that needs to be included
EXTRA_AUTORECONF += "-I ${S}/../aclocal"

