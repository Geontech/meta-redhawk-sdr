SRCTAG = "2.0.1"
PREFERRED_VERSION_omniorb = "4.1.6"

SRC_URI_append = "\
    file://gcc_fix_ApplicationFactory_impl.patch \
"

include redhawk-core.inc

