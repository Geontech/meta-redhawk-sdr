DESCRIPTION = "REDHAWK File Reader Component"

DEPENDS = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"
RDEPENDS_${PN} = "bulkiointerfaces rh-bluefilelib rh-redhawkdevutils"

PR = "1"

COMPONENT_NAME_SDRROOT = "FileReader"

require core-cpp-component.inc

SRC_URI = "git://github.com/redhawksdr/${COMPONENT_NAME};protocol=https;tag=${PV}"

EXTRA_OECONF_append = " --with-expat=${STAGING_EXECPREFIXDIR}"
