DESCRIPTION = "REDHAWK Dev Utils SoftPkg"

PR = "1"

require core-cpp-softpkg.inc

SOFTPKG_NAME = "RedhawkDevUtils"
SRC_URI = "git://github.com/redhawksdr/${SOFTPKG_NAME};protocol=https;tag=${PV}"

# Depends on BULKIO
DEPENDS += "bulkiointerfaces"
RDEPENDS_${PN} += "bulkiointerfaces"
