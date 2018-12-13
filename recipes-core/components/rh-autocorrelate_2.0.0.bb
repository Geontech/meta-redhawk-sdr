DESCRIPTION = "REDHAWK Autocorrelate Component"

DEPENDS = "bulkiointerfaces rh-dsp rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp rh-fftlib"

PR = "7"

LIC_MD5 = "92aadbd9e4b26926809a4e97460613d5"

require core-cpp-component.inc
