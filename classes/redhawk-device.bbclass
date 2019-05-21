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
