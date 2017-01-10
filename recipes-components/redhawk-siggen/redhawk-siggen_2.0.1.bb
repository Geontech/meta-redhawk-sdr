DESCRIPTION = "REDHAWK Core Framework SigGen"
HOMEPAGE = "http://www.redhawksdr.org"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

DEPENDS = "redhawk-bulkio"
RDEPENDS_${PN} = "redhawk-bulkio"

SRC_URI = "git://github.com/RedhawkSDR/SigGen.git;tag=2.0.1;protocol=git \
file://01_Remove_x86_and_Impls.patch;patchdir=.. \
file://03_Add_Missing_Files.patch; \
file://02_Ossie_Check_fix.patch; \
file://04_Prefix_to_SDRROOT.patch; \
"

SRCREV = "eb19c78d10e579d267d3f796f39eae922fc65f0b"

PR = "r0" 

S = "${WORKDIR}/git/cpp"

inherit redhawk-component redhawk-sysroot

FILES_${PN} += "${SDRROOT}/*"
FILES_${PN}-dbg +="${SDRROOT}/dom/components/SigGen/cpp/.debug/SigGen"


