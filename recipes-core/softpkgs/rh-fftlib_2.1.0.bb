DESCRIPTION = "REDHAWK FFTLIB SoftPkg"

PR = "2"

LIC_MD5 = "d4d548a7833916fd41218fdc2430246e"

SOFTPKG_NAME = "fftlib"

require core-cpp-softpkg.inc

DEPENDS += "rh-dsp fftw"
RDEPENDS_${PN} += "rh-dsp libfftwf"

SRC_URI += "file://fftlib-alloc.patch"
