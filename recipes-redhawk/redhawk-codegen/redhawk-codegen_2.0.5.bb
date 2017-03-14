SRC_URI = "git://github.com/RedhawkSDR/core-framework.git;tag=2.0.5;protocol=git"

LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

S = "${WORKDIR}/git/redhawk-codegen"

PREFERRED_VERSION_redhawk-core = "2.0.5"

include redhawk-codegen.inc

