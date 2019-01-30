DESCRIPTION = "REDHAWK AM FM PM Baseband Demod Component"

DEPENDS = "bulkiointerfaces rh-dsp"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp"

PR = "5"

require core-cpp-component.inc
