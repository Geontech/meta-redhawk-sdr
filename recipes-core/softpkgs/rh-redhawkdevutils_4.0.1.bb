DESCRIPTION = "REDHAWK Dev Utils SoftPkg"

PR = "6"

require core-cpp-softpkg.inc

SOFTPKG_NAME = "RedhawkDevUtils"

# Depends on BULKIO
DEPENDS += "bulkiointerfaces"
RDEPENDS_${PN} += "bulkiointerfaces"
