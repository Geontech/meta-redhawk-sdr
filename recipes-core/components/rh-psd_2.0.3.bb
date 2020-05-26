DESCRIPTION = "REDHAWK PSD Component"

DEPENDS = "bulkiointerfaces rh-dsp rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp rh-fftlib"

PR = "6"

require core-cpp-component.inc
