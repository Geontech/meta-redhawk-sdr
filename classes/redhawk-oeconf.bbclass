inherit redhawk-env

CACHED_CONFIGUREVARS += "ossie_cv_ossie_home=${OSSIEHOME} ac_cv_pymod_ossie=yes"

EXTRA_OECONF += "\
    --with-ossie=${OSSIEHOME} \
    OSSIEHOME=${OSSIEHOME} \
    SDRROOT=${SDRROOT} \
    --with-boost=${STAGING_DIR_TARGET}/usr \
    --with-boost-thread=boost_thread \
    "

