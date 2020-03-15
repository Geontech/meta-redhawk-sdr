DESCRIPTION = "REDHAWK AM FM PM Baseband Demod Component"

DEPENDS = "bulkiointerfaces rh-dsp"
RDEPENDS_${PN} = "bulkiointerfaces rh-dsp"

PR = "5"

COMPONENT_NAME_SDRROOT = "AmFmPmBasebandDemod"

require core-cpp-component.inc
