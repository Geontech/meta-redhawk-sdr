DESCRIPTION = "REDHAWK SigGen Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "1"

COMPONENT_NAME = "SigGen"

require core-cpp-component.inc
