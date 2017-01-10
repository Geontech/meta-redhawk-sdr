SRC_URI = "git://github.com/RedhawkSDR/core-framework.git;tag=2.0.4;protocol=git"

LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

S = "${WORKDIR}/git/redhawk/src"

PREFERRED_VERSION_omniorb = "4.2.0"

include redhawk-core.inc


