DESCRIPTION = "REDHAWK AM FM PM Baseband Demod Component"

inherit redhawk-core-cpp-component

DEPENDS = "bulkiointerfaces rh-dsp"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp"

PR = "r5"
