DESCRIPTION = "REDHAWK SinkSDDS Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "2"

COMPONENT_NAME = "SinkSDDS"

require core-cpp-component.inc
