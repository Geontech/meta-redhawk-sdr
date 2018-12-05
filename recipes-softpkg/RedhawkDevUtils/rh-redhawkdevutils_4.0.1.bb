DESCRIPTION = "REDHAWK Dev Utils SoftPkg"

PR = "r6"

# The core-cpp-softpkg class hierarchy handles ...a lot.
inherit redhawk-core-cpp-softpkg

# Depends on BULKIO
DEPENDS += "bulkiointerfaces"
RDEPENDS_${PN} += "bulkiointerfaces"
