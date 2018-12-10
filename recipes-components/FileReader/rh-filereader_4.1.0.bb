DESCRIPTION = "REDHAWK File Reader Component (CPP)"

inherit redhawk-core-cpp-component

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "r4"
