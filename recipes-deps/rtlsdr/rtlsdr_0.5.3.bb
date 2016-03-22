DESCRIPTION = "Driver for RTL-SDR device"
HOMEPAGE = "http://sdr.osmocom.org/trac/wiki/rtl-sdr"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libusb1"
RDEPENDS_${PN} = "libusb1"

SRC_URI = "git://git.osmocom.org/rtl-sdr.git;branch=master;protocol=git \
"

SRCREV = "df9596b2d1ebd36cdb14549cfdd76c25092e14d0"

PR = "r0" 

S = "${WORKDIR}/git"

inherit autotools pkgconfig

