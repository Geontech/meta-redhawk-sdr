inherit redhawk-env

CACHED_CONFIGUREVARS += "\
	ossie_cv_ossie_home=${OSSIEHOME} \
	ossie_cv_sdr_root_target=${SDRROOT_STAGED} \
	ac_cv_pymod_ossie=yes \
	"

EXTRA_OECONF += "\
    --with-ossie=${OSSIEHOME} \
    OSSIEHOME=${OSSIEHOME} \
    SDRROOT=${SDRROOT} \
    --with-boost=${STAGING_EXECPREFIXDIR} \
    --with-boost-thread=boost_thread \
    "

# Patches common to nearly every REDHAWK source
ac_meta_files () {
    touch ${S}/NEWS ${S}/README ${S}/AUTHORS ${S}/ChangeLog
}
do_unpack[postfuncs] += "ac_meta_files"

aclocal_amflags () {
    sed -i 's/ACLOCAL_AMFLAGS = .\+$/ACLOCAL_AMFLAGS = -I m4/g' ${S}/Makefile.am
}
do_patch[postfuncs] += "aclocal_amflags"

# Include the ossie autoconf macros
EXTRA_AUTORECONF += "-I ${OSSIEHOME_STAGED}/share/aclocal/ossie"

CXXFLAGS_append = " -fpermissive -std=gnu++98"
