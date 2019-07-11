DESCRIPTION = "Header for converting SSE intrinsics to NEON"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=7ae2be7fb1637141840314b51970a9f7"

SRC_URI = "git://github.com/DLTcollab/sse2neon;protocol=https;rev=f1389d5a64ca4f528b97d1d738d04eab00d365d4;destsuffix=${P}"

DEPENDS = ""
RDEPENDS_${PN} = ""

COMPATIBLE_MACHINE = "(arm|aarch64)"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install () {
    install -Dm 0644 ${S}/sse2neon.h ${D}${includedir}/sse2neon.h
}
