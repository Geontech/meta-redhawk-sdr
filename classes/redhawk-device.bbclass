inherit autotools-brokensep pkgconfig redhawk-entity

DEPENDS_prepend = "omniorb-native omniorbpy-native "

NODE_CONFIG_SCRIPT ?= ""
do_nodeconfig_patch () {
  if ! [ -z ${NODE_CONFIG_SCRIPT} ] ; then
    sed -i "s/tmp_proc_map.get(tmp_uname_p, 'x86')/'${REDHAWK_PROCESSOR}'/g" ${S}/${NODE_CONFIG_SCRIPT}
  fi
}
do_patch[postfuncs] += "do_nodeconfig_patch"

FILES_${PN} += "${SDRROOT}/dev/devices/*"

# Create a PN-node package for defining a device manager DCD XML
# Define RH_NODE_NAME in you recipe to control this behavior.
# Ensure your recipe provides a SDRROOT/dev/nodes/RH_NODE_NAME/DeviceManager.dcd.xml
# in its PN-node package.
RH_NODE_NAME ?= "DevMgr-${PN}"
PN_NODE = "${PN}-node"
PACKAGE_BEFORE_PN += "${PN_NODE}"
RDEPENDS_${PN_NODE} = "${PN}"
FILES_${PN_NODE} = "${SDRROOT}/dev/nodes/*"

# Create a PN-init package for sysvinit or systemd that depends on a node definition package
PN_INIT = "${PN}-init"
PACKAGE_BEFORE_PN += "${PN_INIT}"
FILESEXTRAPATHS_prepend := "${META_REDHAWK_SDR_NODE_INIT}:"
INIT_FILE_BASE_NAME = "redhawk-node-${PN}"
FILES_${PN_INIT} = ""
RDEPENDS_${PN_INIT} = "${PN}-node"

inherit update-rc.d
SRC_URI_append = " file://node-init.d"
INITSCRIPT_PACKAGES = "${PN_INIT}"
INITSCRIPT_NAME_${PN_INIT} = "${INIT_FILE_BASE_NAME}"
INITSCRIPT_PARAMS_${PN_INIT} = "start 99 2 3 4 5 . stop 01 0 1 6 ."
FILES_${PN_INIT} += "${sysconfdir}/init.d/${INIT_FILE_BASE_NAME}"

inherit systemd
SRC_URI_append = " file://node-systemd"
SYSTEMD_PACKAGES = "${PN_INIT}"
SYSTEMD_SERVICE_${PN_INIT} = "${INIT_FILE_BASE_NAME}.service"
SYSTEMD_AUTO_ENABLE = "enable"
FILES_${PN_INIT} += "${systemd_system_unitdir}/${INIT_FILE_BASE_NAME}.service"

do_compile_append () {
    SOURCE_FILE=""
    TARGET_FILE=""
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        SOURCE_FILE="node-init.d"
        TARGET_FILE="${INIT_FILE_BASE_NAME}"
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        SOURCE_FILE="node-systemd"
        TARGET_FILE="${INIT_FILE_BASE_NAME}.service"
    fi
    cp -f ${WORKDIR}/${SOURCE_FILE} ${B}/${TARGET_FILE}
    sed -i "s|NODE_NAME|${RH_NODE_NAME}|g"      ${B}/${TARGET_FILE}
    sed -i "s|DOMAIN_NAME|${REDHAWK_DOMAIN}|g"  ${B}/${TARGET_FILE}
}

do_install_append () {
    if ${@bb.utils.contains('DISTRO_FEATURES','sysvinit','true','false',d)}; then
        install -Dm 0755 ${B}/${INIT_FILE_BASE_NAME} ${D}${sysconfdir}/init.d/${INIT_FILE_BASE_NAME}
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -Dm 0644 ${B}/${INIT_FILE_BASE_NAME}.service ${D}${systemd_system_unitdir}/${INIT_FILE_BASE_NAME}.service
    fi
}