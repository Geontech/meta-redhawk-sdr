SRC_URI = "git://github.com/RedhawkSDR/core-framework.git;tag=2.0.5;protocol=git"

LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

S = "${WORKDIR}/git/GPP/cpp"

PREFERRED_VERSION_redhawk-bulkio = "2.0.5"

SRC_URI_append = "\
    file://01_Clear_AMFLAGS_GPP_NO_TEST.patch \
    file://02_armv7l_default_2.0.4.patch \
    file://configure-gpp_post-2.0.4 \
    file://04_GPP_spd_armv7l.patch;patchdir=${WORKDIR}/git/GPP \
"

# ################################################
# End user-controlled variables to adjust the node
# ################################################
RH_GPP_MCASTNIC     ?= ""
RH_GPP_NODE_NAME    ?= "DevMgr-GPP-${MACHINE}"
RH_GPP_NAME         ?= "GPP-${TARGET_ARCH}"
# ################################################

include gpp.inc
include gpp_install_append_post_2.0.4.inc

