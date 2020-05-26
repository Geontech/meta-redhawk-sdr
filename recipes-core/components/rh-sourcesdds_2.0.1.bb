DESCRIPTION = "REDHAWK SourceSDDS Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "1"

COMPONENT_NAME = "SourceSDDS"

require core-cpp-component.inc
