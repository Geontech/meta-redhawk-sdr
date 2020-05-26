DESCRIPTION = "REDHAWK File Reader Component"

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "1"

require core-cpp-component.inc

COMPONENT_NAME = "FileReader"

EXTRA_OECONF_append = " --with-expat=${STAGING_EXECPREFIXDIR}"
