DESCRIPTION = "REDHAWK Tune Filter Decimate Component"

DEPENDS = "bulkiointerfaces rh-dsp rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp rh-fftlib"

PR = "1"

COMPONENT_NAME = "TuneFilterDecimate"

require core-cpp-component.inc
