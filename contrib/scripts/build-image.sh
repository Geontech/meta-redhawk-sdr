#!/bin/bash
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
# This script is derived from Phillip Balister work on the meta-sdr repository found
# here: http://github.com/balister/meta-sdr.git
# 

echo MACHINE:     "${MACHINE:?Need to set to an available machine}"
echo BUILD_IMAGE: "${BUILD_IMAGE:?Need to set, e.g., 'redhawk-base-image'}"

echo STEP 1 - Starting Bit Bake
if ! bitbake ${BUILD_IMAGE}; then
	echo Bitbake build image failed
	exit 1
fi

echo STEP 2 - Building SD Card Image
rm -rf images/${MACHINE}/build
if ! wic create ../meta-redhawk-sdr/contrib/wks/sdimage-8G.wks -e ${BUILD_IMAGE} -o images/${MACHINE}; then
	echo Bitbake build image to SD Card build failed
	exit 1
fi

echo STEP 3 - Building tools to run wic
if ! bitbake parted-native mtools-native dosfstools-native; then
	echo Failed to build tools needed to run wic.
	exit 1
fi

echo STEP 4 - Packaging image
export IMAGE_NAME=`ls images/${MACHINE}/build/sdimage*mmcblk.direct`
xz -k ${IMAGE_NAME}
mv ${IMAGE_NAME}.xz images/${MACHINE}/sdimage-${BUILD_IMAGE}.direct.xz
mv ${IMAGE_NAME} images/${MACHINE}/sdimage-${BUILD_IMAGE}.direct
md5sum images/${MACHINE}/sdimage-${BUILD_IMAGE}.direct.xz > images/${MACHINE}/sdimage-${BUILD_IMAGE}.direct.xz.md5

echo FINISHED:
echo    Archive is at: images/${MACHINE}/sdimage-${BUILD_IMAGE}.direct.xz
echo    Next: sudo dd if=images/${MACHINE}/sdimage-${BUILD_IMAGE}.direct of=SD CARD bs=1M


