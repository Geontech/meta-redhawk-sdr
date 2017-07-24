# Inherit the core repo references, etc.
include recipes-core/include/redhawk-repo.inc

# Inherit the base softpkg class
inherit autotools-brokensep pkgconfig redhawk-softpkg

# Derive the softpkg's parent directory name which (should) be the installed softpkg name and location in the
# core repo.
SOFTPKG_NAME ?= "${@'${THISDIR}'.split('/')[-1]}"
SOFTPKG_REPO_DIR ?= "${SOFTPKG_NAME}"

# Append SOFTPKG_PREFIX
SOFTPKG_PREFIX .= "/rh/${SOFTPKG_NAME}"

# Set source directory
S = "${WORKDIR}/git/redhawk-sharedlibs/${SOFTPKG_REPO_DIR}/cpp"
