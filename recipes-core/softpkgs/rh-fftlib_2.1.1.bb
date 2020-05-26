DESCRIPTION = "REDHAWK FFTLIB SoftPkg"

PR = "2"

SOFTPKG_NAME = "fftlib"

require core-cpp-softpkg.inc

DEPENDS += "rh-dsp fftw"
RDEPENDS_${PN} += "rh-dsp libfftwf"

SRC_URI += "file://fftlib-alloc.patch"
