REDHAWK_VERSION = "2.0.8"

SRC_URI = "gitsm://github.com/RedhawkSDR/redhawk.git;tag=${REDHAWK_VERSION};branch=master-2.0;protocol=git"

HOMEPAGE = "http://www.redhawksdr.org"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/redhawk-core-framework/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"


# Dynamic architecture patch for whatever ${REDHAWK_PROCESSOR} is set to.
# 1. This takes whatever is ${NODE_CONFIG_SCRIPT} and find where it hard-codes the architecture look-up,
#    replacing it for ${REDHAWK_PROCESSOR}
# 2. This changes processor name x86_64 and replaces it with ${REDHAWK_PROCESSOR}.
# 3. This removes processor name x86.
REDHAWK_PROCESSOR ?= "x86_64"
REDHAWK_PROCESSOR_zynq ?= "armv7l"
REDHAWK_PROCESSOR_zynqmp ?= "aarch64"
