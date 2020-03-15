DESCRIPTION = "REDHAWK SinkSDDS Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "5"

COMPONENT_NAME_SDRROOT = "SinkSDDS"

require core-cpp-component.inc
