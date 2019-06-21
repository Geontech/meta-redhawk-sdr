# Package group definitions for REDHAWK builds

LICENSE = "LGPL-3.0"
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

inherit packagegroup

PACKAGES = "\
    packagegroup-redhawk-basic-components \
    "

PROVIDES = "${PACKAGES}"

# This package group builds all of the avilable components (which may incidentally
# install some softpkg dependencies).
# TODO: Patch in Python and Java implementations where applicable
# TODO: Add fcalc component (Python only)
# TODO: Add DataConverter (requires adding NEON support vs. SSEx)
SUMMARY_packagegroup-redhawk-basic-components = "All available REDHAWK Components (CPP)"
RDEPENDS_packagegroup-redhawk-basic-components = "\
    rh-agc \
    rh-amfmpmbasebanddemod \
    rh-arbitraryrateresampler \
    rh-autocorrelate \
    rh-fastfilter \
    rh-filereader \
    rh-filewriter \
    rh-hardlimit \
    rh-psd \
    rh-psk-soft \
    rh-rbdsdecoder \
    rh-siggen \
    rh-sinksdds \
    rh-sinksocket \
    rh-sourcesdds \
    rh-sourcesocket \
    rh-tunefilterdecimate \
    rh-sinkvita49 \
    rh-sourcevita49 \
    "
