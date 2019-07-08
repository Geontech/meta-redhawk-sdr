DESCRIPTION = "REDHAWK SinkVITA49"

DEPENDS = "bulkiointerfaces rh-vita49"
RDEPENDS_${PN} = "bulkiointerfaces rh-vita49"

PR = "1"

require core-cpp-component.inc

SRC_URI = "git://github.com/redhawksdr/${COMPONENT_NAME};protocol=https;tag=${PV}"
