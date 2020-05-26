DESCRIPTION = "REDHAWK Autocorrelate Component"

DEPENDS = "bulkiointerfaces rh-dsp rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp rh-fftlib"

PR = "1"

require core-cpp-component.inc
