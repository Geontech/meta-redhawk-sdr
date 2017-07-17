DESCRIPTION = "REDHAWK Core Framework SigGen"

include recipes-core/include/redhawk-repo.inc
inherit redhawk-component

DEPENDS = "redhawk-bulkio"
RDEPENDS_${PN} = "redhawk-bulkio"

PR = "r1"

SRC_URI_append = " \
	file://01_Remove_x86_and_Impls.patch;patchdir=.. \
	file://03_Add_Missing_Files.patch; \
	file://04_Prefix_to_SDRROOT.patch; \
"

S = "${WORKDIR}/git/redhawk-components/SigGen/cpp"

FILES_${PN} += "\
	${SDRROOT}/dom/components/rh/SigGen/*.xml \
	${SDRROOT}/dom/components/rh/SigGen/cpp/SigGen \
	"
FILES_${PN}-dbg += "${SDRROOT}/dom/components/rh/SigGen/cpp/.debug"

