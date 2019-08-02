#
# This file is protected by Copyright. Please refer to the COPYRIGHT file distributed 
# with this source distribution.
#
# This file is part of Geon Technology's meta-redhawk-sdr.
#
# Geon Technology's meta-redhawk-sdr is free software: you can redistribute it and/or 
# modify it under the terms of the GNU Lesser General Public License as published by 
# the Free Software Foundation, either version 3 of the License, or (at your option) 
# any later version.
#
# Geon Technology's meta-redhawk-sdr is distributed in the hope that it will be useful, 
# but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
# details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see http://www.gnu.org/licenses/.
#
SUMMARY ?= "Basic REDHAWK image"
LICENSE ?= "LGPL-3.0"

inherit core-image redhawk-env

IMAGE_FEATURES_append = "\
    ssh-server-openssh \
    package-management \
    "

IMAGE_INSTALL_append = " packagegroup-redhawk-core"

# Add a timestamp to the image in /etc/version-image
# Credit: Philip Balister
add_image_version_info() {
    touch ${IMAGE_ROOTFS}/etc/version-image
    echo "Timestamp:" `date -u +%4Y%2m%2d%2H%2M` >> ${IMAGE_ROOTFS}/etc/version-image
    echo "Release: ${RELEASE_NAME}" >> ${IMAGE_ROOTFS}/etc/version-image 
    echo "Image: ${PN}" >> ${IMAGE_ROOTFS}/etc/version-image 
}
ROOTFS_POSTPROCESS_COMMAND += "add_image_version_info ; "

# Collect SDRROOT assets w/ installer for hybrid domain
redhawk_sdrroot_installer() {
    MRSS=${META_REDHAWK_SDR_SCRIPTS}
    SDRROOT_TAR=${DEPLOY_DIR_IMAGE}/${PN}-${PV}-sdrroot.tar.gz
    rm -f ${SDRROOT_TAR}

    cd ${IMAGE_ROOTFS}/${SDRROOT}
    PROJECTS="$(find dom/{mgr/rh/ComponentHost,components,deps} -iname *.spd.xml | while read spd; do echo $(dirname $spd); done;)"
    PROJECTS="${PROJECTS//$'\n'/ }"
    tar -czf ${SDRROOT_TAR} ${PROJECTS} -C ${MRSS} spd_utility install_assets
}
ROOTFS_POSTPROCESS_COMMAND += "redhawk_sdrroot_installer ; "
