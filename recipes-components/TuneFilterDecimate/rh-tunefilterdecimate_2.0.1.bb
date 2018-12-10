DESCRIPTION = "REDHAWK Tune Filter Decimate Component (CPP)"

inherit redhawk-core-cpp-component

DEPENDS = "bulkiointerfaces rh-dsp rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp rh-fftlib"

PR = "r5"
