DESCRIPTION = "REDHAWK SinkVITA49 (CPP)"

inherit redhawk-core-cpp-component

DEPENDS = "bulkiointerfaces rh-vita49"
RDEPENDS_${PN} = "bulkiointerfaces rh-vita49"

PR = "r3"
