DESCRIPTION = "REDHAWK SinkVITA49"

DEPENDS = "bulkiointerfaces rh-vita49"
RDEPENDS_${PN} = "bulkiointerfaces rh-vita49"
SRC_URI_append = " \
	file://multicast.patch \
	file://unicast.patch \
	file://unicast_tcp.patch \
	"
PR = "3"

COMPONENT_NAME_SDRROOT = "SinkVITA49"

require core-cpp-component.inc
