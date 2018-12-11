require redhawk-${REDHAWK_VERSION}.inc

# Dynamic architecture patch for whatever ${REDHAWK_PROCESSOR} is set to.
# 1. This takes whatever is ${NODE_CONFIG_SCRIPT} and find where it hard-codes the architecture look-up,
#    replacing it for ${REDHAWK_PROCESSOR}
# 2. This changes processor name x86_64 and replaces it with ${REDHAWK_PROCESSOR}.
# 3. This removes processor name x86.
REDHAWK_PROCESSOR ?= "x86_64"
REDHAWK_PROCESSOR_rpi ?= "armv7l"
REDHAWK_PROCESSOR_zynq ?= "armv7l"
REDHAWK_PROCESSOR_zynqmp ?= "aarch64"