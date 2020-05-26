DESCRIPTION = "REDHAWK File Writer Component (CPP)"

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "1"

require core-cpp-component.inc

COMPONENT_NAME = "FileWriter"

SRC_URI_append_arm = " file://0001-no-longdouble.patch"