DESCRIPTION = "REDHAWK Arbitrary Rate Resampler Component (CPP)"

DEPENDS = "bulkiointerfaces rh-dsp"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp"

PR = "4"

COMPONENT_NAME = "ArbitraryRateResampler"

require core-cpp-component.inc
