DESCRIPTION = "REDHAWK File Writer Component (CPP)"

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "1"

require core-cpp-component.inc

SRC_URI = "git://github.com/redhawksdr/${COMPONENT_NAME};protocol=https;tag=${PV}"
SRC_URI_append_arm = " file://0001-no-longdouble.patch"