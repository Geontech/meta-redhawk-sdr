# Inherit the core repo references, etc.
include recipes-core/include/redhawk-repo.inc

# Inherit the base component class
inherit redhawk-component

# Derive the component's parent directory name which (should) be the installed component name and location in the
# core repo.
COMPONENT_NAME="${@'${THISDIR}'.split('/')[-1]}"

S = "${WORKDIR}/git/redhawk-components/${COMPONENT_NAME}/cpp"

FILES_${PN} += "${SDRROOT}/*"
FILES_${PN}-dbg += "${SDRROOT}/dom/components/rh/${COMPONENT_NAME}/cpp/.debug"
