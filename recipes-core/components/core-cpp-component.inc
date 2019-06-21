require recipes-core/core-framework/core-framework.inc

# Inherit the base component class
inherit redhawk-component

# Derive the component's parent directory name which (should) be the installed component name and location in the
# core repo.
COMPONENT_NAME ?= "${@'${PN}'.split('rh-')[-1]}"

SRC_URI = "git://github.com/redhawksdr/${COMPONENT_NAME};protocol=https;tag=${PV}-${PR}"

S = "${WORKDIR}/git/cpp"

FILES_${PN} += "${SDRROOT}/*"
FILES_${PN}-dbg += "${SDRROOT}/dom/components/rh/${COMPONENT_NAME}/*/.debug"
