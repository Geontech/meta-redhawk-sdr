
# Install the script
do_install_append() {
    install -d ${D}/etc/init.d
    sed -i "s|MCASTNIC|${RH_GPP_MCASTNIC}|g" ${WORKDIR}/configure-gpp_post-2.0.4
    install -m 0755 ${WORKDIR}/configure-gpp_post-2.0.4 ${D}/etc/init.d/configure-gpp
    
    # Run node creator (containing just this device)
    ${D}${SDRROOT}/dev/devices/GPP/cpp/gpp_setup \
        --sdrroot="${D}${SDRROOT}" \
        --nodename="${RH_GPP_NODE_NAME}" \
        --gppname="${RH_GPP_NAME}"
}
