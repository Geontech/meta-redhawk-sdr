require recipes-core/core-framework/core-assets-2.2.6.inc

# Inherit the base softpkg class
inherit redhawk-softpkg

# Append SOFTPKG_PREFIX
SOFTPKG_PREFIX .= "/rh/${SOFTPKG_NAME}"

S = "${WORKDIR}/git/sdr/libraries/${SOFTPKG_NAME}/cpp"
