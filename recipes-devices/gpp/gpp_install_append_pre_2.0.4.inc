
# Install the script
do_install_append() {
    install -d ${D}/etc/init.d
    sed -i "s|MCASTNIC|${RH_GPP_MCASTNIC}|g" ${WORKDIR}/configure-gpp_pre-2.0.4
    install -m 0755 ${WORKDIR}/configure-gpp_pre-2.0.4 ${D}/etc/init.d/configure-gpp
    
    # Run create_node
    ${D}${SDRROOT}/dev/devices/GPP/cpp/create_node.py \
        --sdrroot="${D}${SDRROOT}" \
        --nodename="${RH_GPP_NODE_NAME}"
}
