DESCRIPTION = "REDHAWK fastfilter Component"

DEPENDS = "bulkiointerfaces rh-dsp rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp rh-fftlib"

PR = "4"

require core-cpp-component.inc
