inherit redhawk-env

redhawk_core_ossie_sysroot () {
    sysroot_stage_dir ${D}${OSSIEHOME} \ 
        ${SYSROOT_DESTDIR}${OSSIEHOME}
}
redhawk_core_sdrroot_sysroot () {
    sysroot_stage_dir ${D}${SDRROOT} \ 
        ${SYSROOT_DESTDIR}${SDRROOT}
}
SYSROOT_PREPROCESS_FUNCS += "redhawk_core_ossie_sysroot"
SYSROOT_PREPROCESS_FUNCS += "redhawk_core_sdrroot_sysroot"
