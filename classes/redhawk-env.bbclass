# Set install location OSSIEHOME and SDRROOT
OSSIEHOME = "/usr/local/redhawk/core"
SDRROOT = "/usr/local/redhawk/sdr"

# STAGED Versions of thse variables (where machine-specific versions are temporarily held,
# libs, etc.)
OSSIEHOME_STAGED="${STAGING_DIR}/${MACHINE}${OSSIEHOME}"
SDRROOT_STAGED="${STAGING_DIR}/${MACHINE}${SDRROOT}"
