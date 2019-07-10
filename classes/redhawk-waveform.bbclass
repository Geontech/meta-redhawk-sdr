
inherit redhawk-sysroot

FILES_${PN} += "${SDRROOT}/dom/waveforms/*"

do_install () {
  for path in $(find ${S} -name "*.sad.xml"); do
    wf_file_name=$(basename ${path})
    wf_name=${wf_file_name%.sad.xml}

    install -d ${D}${SDRROOT}/dom/waveforms/${wf_name}
    install -m 0644 ${path} ${D}${SDRROOT}/dom/waveforms/${wf_name}/${wf_file_name}
  done
}
