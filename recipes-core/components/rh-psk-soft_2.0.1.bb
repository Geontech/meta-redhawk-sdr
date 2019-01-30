DESCRIPTION = "REDHAWK PSK Soft (Decision) Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "5"

COMPONENT_NAME = "psk_soft"

require core-cpp-component.inc
