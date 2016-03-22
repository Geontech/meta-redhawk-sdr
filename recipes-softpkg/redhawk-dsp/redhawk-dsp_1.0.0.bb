DESCRIPTION = "REDHAWK Core Framework GPP"
HOMEPAGE = "http://www.redhawksdr.org"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

DEPENDS = "redhawk-bulkio"
RDEPENDS_${PN} = "redhawk-bulkio"

SRC_URI = " \
    git://github.com/RedhawkSDR/dsp.git;branch=master;protocol=git \
    file://03_Add_Missing_Files.patch \
    "

SRCREV = "2b3dfb93b5f836e66768f582312e4266cae52cd6"

PR = "r0" 

S = "${WORKDIR}/git/cpp"


# We have to inherit from pythonnative if we do stuff with the system python.
# autotools-brokensep is the sasme as autotools but our build and src locations are the same since we cannot build away from our src.
inherit autotools-brokensep pkgconfig pythonnative redhawk-sysroot redhawk-entity

FILES_${PN} += "${SDRROOT}/*"
FILES_${PN}-dbg += "${SDRROOT}/dom/deps/dsp/cpp/lib/.debug/*"
FILES_${PN}-dev += "${SDRROOT}/dom/deps/dsp/cpp/lib/libdsp.so"
FILES_${PN}-staticdev += "${SDRROOT}/dom/deps/dsp/cpp/lib/libdsp.a"

# Soft packages have an additional aclocal that needs to be included
EXTRA_AUTORECONF += "-I ${S}/../aclocal"

