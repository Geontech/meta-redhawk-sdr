DESCRIPTION = "REDHAWK RBDS Decoder Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "1"

COMPONENT_NAME = "RBDSDecoder"

require core-cpp-component.inc

SRC_URI_append_arm = "\
    file://arm-no-hard-float.configure.ac.patch \
"
