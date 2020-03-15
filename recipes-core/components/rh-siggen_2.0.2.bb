DESCRIPTION = "REDHAWK SigGen Component"

DEPENDS = "bulkiointerfaces"
RDEPENDS_${PN} = "bulkiointerfaces"

PR = "1"

COMPONENT_NAME_SDRROOT = "SigGen"

require core-cpp-component.inc

SRC_URI = "git://github.com/redhawksdr/${COMPONENT_NAME};protocol=https;tag=${PV}"
