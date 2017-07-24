DESCRIPTION = "REDHAWK Arbitrary Rate Resampler Component (CPP)"

inherit redhawk-core-cpp-component

DEPENDS = "bulkiointerfaces rh-dsp"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp"

PR = "r2"
