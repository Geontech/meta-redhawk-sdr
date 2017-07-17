DESCRIPTION = "Driver for RTL-SDR device"
HOMEPAGE = "http://sdr.osmocom.org/trac/wiki/rtl-sdr"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libusb1"
RDEPENDS_${PN} = "libusb1"

SRC_URI = "\
    https://github.com/RedhawkSDR/librtlsdr;tag=${PV};protocol=git \
    file://01_fix_pkgconfig.patch \
    "

S = "${WORKDIR}/git"

inherit autotools pkgconfig
