require redhawk-${REDHAWK_VERSION}.inc

# In several places, the processor architecture may not match any available variable, but
# will need to match the response of uname in the running system.  Patches in this layer
# address that need using this variable, which can be overridden as needed.
REDHAWK_PROCESSOR ?= "${HOST_ARCH}"
REDHAWK_PROCESSOR_rpi ?= "armv7l"
REDHAWK_PROCESSOR_ettus-e300 = "armv7l"
REDHAWK_PROCESSOR_zynq ?= "armv7l"
REDHAWK_PROCESSOR_zynqmp ?= "aarch64"
