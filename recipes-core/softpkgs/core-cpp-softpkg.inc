require recipes-core/core-framework/core-framework.inc

# Inherit the base softpkg class
inherit redhawk-softpkg

# Append SOFTPKG_PREFIX
SOFTPKG_PREFIX .= "/rh/${SOFTPKG_NAME}"

SRC_URI = "git://github.com/redhawksdr/${SOFTPKG_NAME};protocol=https;tag=${PV}-${PR}"
S = "${WORKDIR}/git/cpp"
