DESCRIPTION = "REDHAWK File Reader Component"

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "1"

require core-cpp-component.inc

SRC_URI = "git://github.com/redhawksdr/${COMPONENT_NAME};protocol=https;tag=${PV}"
