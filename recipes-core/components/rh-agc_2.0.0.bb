DESCRIPTION = "REDHAWK AGC Component (CPP)"

DEPENDS = "bulkiointerfaces rh-dsp"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp"

PR = "5"

require core-cpp-component.inc
