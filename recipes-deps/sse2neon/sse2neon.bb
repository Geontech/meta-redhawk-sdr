DESCRIPTION = "Header for converting SSE intrinsics to NEON"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=7ae2be7fb1637141840314b51970a9f7"

SRC_URI = "git://github.com/DLTcollab/sse2neon;protocol=https;rev=f8c710409c9f8f440c9d187a47643988586ffff6;destsuffix=${P}"

DEPENDS = ""
RDEPENDS_${PN} = ""

COMPATIBLE_MACHINE ?= "^$"
COMPATIBLE_MACHINE_arm = "${MACHINE}"
COMPATIBLE_MACHINE_aarch64 = "${MACHINE}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install () {
    install -Dm 0644 ${S}/sse2neon.h ${D}${includedir}/sse2neon.h
}
