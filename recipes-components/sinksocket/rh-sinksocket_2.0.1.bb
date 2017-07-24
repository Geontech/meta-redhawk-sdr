DESCRIPTION = "REDHAWK Sink Socket Component (CPP)"

inherit redhawk-core-cpp-component

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "r1"
