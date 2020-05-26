DESCRIPTION = "REDHAWK DataConverter Component"

DEPENDS = "bulkiointerfaces rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-fftlib"

PR = "1"

COMPONENT_NAME = "DataConverter"

require core-cpp-component.inc

############################
## ARM NEON SUPPORT BELOW ##
############################

FILES_SSE2NEON = "\
    file://0001-msse-configure.ac.patch \
    file://0002-arm-neon-mathOptimizations.h.patch \
    file://0003-sse2-or-arm-mathOptimizations.c.patch \
    "
SRC_URI_append_arm = " ${FILES_SSE2NEON}"
SRC_URI_append_aarch64 = " ${FILES_SSE2NEON}"

DEPENDS_append_arm = " sse2neon"
DEPENDS_append_aarch64 = " sse2neon"
