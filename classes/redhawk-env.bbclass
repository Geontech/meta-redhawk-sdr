# Set install location OSSIEHOME and SDRROOT
OSSIEHOME = "${prefix}/redhawk-sdr/core"
SDRROOT = "${localstatedir}/redhawk-sdr/sdr"

# STAGED Versions of these variables (where machine-specific versions are temporarily held,
# libs, etc.)
OSSIEHOME_STAGED="${STAGING_DIR_TARGET}${OSSIEHOME}"
SDRROOT_STAGED="${STAGING_DIR_TARGET}${SDRROOT}"

OSSIEHOME_STAGED_NATIVE="${STAGING_DIR_NATIVE}${OSSIEHOME}"
SDRROOT_STAGED_NATIVE="${STAGING_DIR_NATIVE}${SDRROOT}"

fakeroot do_redhawk_owns_sdrroot() {
    if [ -d "${D}${SDRROOT}" ]; then
        chown -f -R ${REDHAWK_UID}:${REDHAWK_GID} ${D}${SDRROOT}
    fi
}
do_install[postfuncs] += "do_redhawk_owns_sdrroot"
