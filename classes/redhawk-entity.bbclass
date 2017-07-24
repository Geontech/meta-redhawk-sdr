# This file sets OSSIEHOME and other environment variables used by autotools
inherit redhawk-oeconf redhawk-sysroot pythonnative

# Needed so that when the python distutils is run it can get the system prefix which, since it's the build system python will be /.../x86_64-linux/usr and replace it with our host systems name.
do_configure_prepend() {
  export BUILD_SYS=${BUILD_SYS}
  export HOST_SYS=${HOST_SYS}
  export STAGING_INCDIR=${STAGING_INCDIR}
  export STAGING_LIBDIR=${STAGING_LIBDIR}
  export PKG_CONFIG_PATH="${OSSIEHOME_STAGED}/lib/pkgconfig:${PKG_CONFIG_PATH}"
  export PYTHONPATH=${OSSIEHOME_STAGED}/lib/python:${PYTHONPATH}
  export PATH="${OSSIEHOME_STAGED}/bin:${PATH}"

  # Common patches among device and component as noted by YLB.
  # These were individual patch files but were being tacked on to many recipes.
  # This is to reduce some clutter.
  sed -i 's/ACLOCAL_AMFLAGS = .\+$/ACLOCAL_AMFLAGS = -I m4/g' Makefile.am
  sed -i 's/xmldir = $(prefix)/xmldir = $(SDR_ROOT)/g' Makefile.am
  sed -i 's/bindir = $(prefix)/bindir = $(SDR_ROOT)/g' Makefile.am
  sed -i 's/domdir = $(prefix)/domdir = $(SDR_ROOT)/g' Makefile.am
  sed -i 's,${prefix}/dom/deps,${SDR_ROOT}/dom/deps,g' configure.ac
  touch ./NEWS ./README ./AUTHORS ./ChangeLog
}

# Needed so that when the python distutils is run it can get the system prefix.
do_install_prepend() {
  export BUILD_SYS=${BUILD_SYS}
  export HOST_SYS=${HOST_SYS}
  export STAGING_INCDIR=${STAGING_INCDIR}
  export STAGING_LIBDIR=${STAGING_LIBDIR}
  export PKG_CONFIG_PATH="${OSSIEHOME_STAGED}/lib/pkgconfig:${PKG_CONFIG_PATH}"
  export PYTHONPATH=${OSSIEHOME_STAGED}/lib/python:${PYTHONPATH}
}

# Dynamic architecture patch for whatever ${PACKAGE_ARCH} is set to.
# 1. This takes whatever is ${NODE_CONFIG_SCRIPT} and find where it hard-codes the architecture look-up,
#    replacing it for ${PACKAGE_ARCH}
# 2. This changes processor name x86_64 and replaces it with ${PACKAGE_ARCH}.
# 3. This removes processor name x86.
NODE_CONFIG_SCRIPT ?= ""
do_dynamic_arch_patch () {
    if ! [ -z ${NODE_CONFIG_SCRIPT} ] ; then 
        sed -i "s/tmp_proc_map.get(tmp_uname_p, 'x86')/'${PACKAGE_ARCH}'/g" ${S}/${NODE_CONFIG_SCRIPT} 
    fi
    sed -i "s/<processor name=\"x86_64\"\/>/<processor name=\"${PACKAGE_ARCH}\"\/>/g" ${S}/../*.spd.xml
    sed -i "s/<processor name=\"x86\"\/>//g" ${S}/../*.spd.xml
}
addtask dynamic_arch_patch after do_patch before do_configure
