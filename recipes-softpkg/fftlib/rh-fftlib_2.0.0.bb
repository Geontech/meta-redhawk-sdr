DESCRIPTION = "REDHAWK FFTLIB SoftPkg"

PR = "r1"

# The core-cpp-softpkg class hierarchy handles ...a lot.
inherit redhawk-core-cpp-softpkg

SRC_URI_append = "\
	file://fftw3_removal.patch \
	"

DEPENDS += "rh-dsp fftw"
RDEPENDS_${PN} += "rh-dsp libfftwf"
