DESCRIPTION = "Driver for RTL-SDR device maintained by REDHAWK SDR"

inherit autotools pkgconfig

HOMEPAGE = "http://sdr.osmocom.org/trac/wiki/rtl-sdr"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "libusb1"
RDEPENDS_${PN} = "libusb1"

PR = "4"

SRC_URI_append = "\
    git://github.com/redhawksdr/librtlsdr;protocol=https;branch=redhawk-v.${PV};tag=${PV}-${PR} \
    file://01_fix_pkgconfig.patch \
    "

S = "${WORKDIR}/git"
