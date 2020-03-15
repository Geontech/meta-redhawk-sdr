HOMEPAGE = "http://www.redhawksdr.org"
LICENSE = "LGPL-3.0"
LIC_MD5 ?= "e6a600fd5e1d9cbde2d983680233ad02"
LIC_FILES_CHKSUM ?= "file://${WORKDIR}/git/LICENSE;md5=${LIC_MD5}"

# need to export these variables for python-config to work
export PYTHONPATH
export BUILD_SYS
export HOST_SYS
export STAGING_INCDIR
export STAGING_LIBDIR
