SRCTAG = "2.0.1"
PREFERRED_VERSION_redhawk-bulkio = "2.0.1"

SRC_URI_append = "\
    file://01_Clear_AMFLAGS_GPP.patch \
    file://02_armv7l_default_pre-2.0.4.patch \
    file://configure-gpp_pre-2.0.4 \
    file://04_GPP_spd_armv7l.patch;patchdir=${WORKDIR}/git \
"

include gpp.inc
include gpp_install_append_pre_2.0.4.inc

