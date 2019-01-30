DESCRIPTION = "REDHAWK PSD Component"

DEPENDS = "bulkiointerfaces rh-dsp rh-fftlib"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp rh-fftlib"

PR = "6"

LIC_MD5 = "d32239bcb673463ab874e80d47fae504"

require core-cpp-component.inc
