DESCRIPTION = "REDHAWK FFTLIB SoftPkg"

PR = "r2"

# The core-cpp-softpkg class hierarchy handles ...a lot.
inherit redhawk-core-cpp-softpkg

SRC_URI_append = "\
	"

DEPENDS += "rh-dsp fftw"
RDEPENDS_${PN} += "rh-dsp libfftwf"
