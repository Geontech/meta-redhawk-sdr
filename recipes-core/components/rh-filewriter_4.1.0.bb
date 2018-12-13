DESCRIPTION = "REDHAWK File Writer Component (CPP)"

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "4"

require core-cpp-component.inc
