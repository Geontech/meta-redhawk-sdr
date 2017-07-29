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
do_configure_prepend () {
  touch ./NEWS ./README ./AUTHORS ./ChangeLog
  sed -i 's/ACLOCAL_AMFLAGS = .\+$/ACLOCAL_AMFLAGS = -I m4/g' Makefile.am
}