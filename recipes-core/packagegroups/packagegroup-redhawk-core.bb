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
    packagegroup-redhawk-core \
    "

PROVIDES = "${PACKAGES}"

# This package is the bare essentials for the core framework on a device.  The result is
# actually enough to standup a Domain manager (nodeBooter -D), however its purpose is to
# collect the basics for a REDHAWK system.

SUMMARY_packagegroup-redhawk-core = "Basic packages for the REDHAWK Core Framework (enough to be a Domain)."
RDEPENDS_packagegroup-redhawk-core = "\
    redhawk \
    redhawk-python \
    frontendinterfaces \
    frontendinterfaces-python \
    bulkiointerfaces \
    bulkiointerfaces-python \
    burstiointerfaces \
    burstiointerfaces-python \
    redhawk-codegen \
    "

