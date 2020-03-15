DESCRIPTION = "REDHAWK RBDS Decoder Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "5"

LIC_MD5 = "92aadbd9e4b26926809a4e97460613d5"

COMPONENT_NAME_SDRROOT = "RBDSDecoder"

require core-cpp-component.inc

SRC_URI_append = "\
    file://arm-no-hard-float.configure.ac.patch \
"
