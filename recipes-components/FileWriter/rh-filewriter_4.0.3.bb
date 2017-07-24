DESCRIPTION = "REDHAWK File Writer Component (CPP)"

inherit redhawk-core-cpp-component

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "r1"
