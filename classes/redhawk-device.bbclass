inherit autotools-brokensep pkgconfig redhawk-entity

DEPENDS_prepend = "omniorb-native omniorbpy-native "

FILES_${PN} += "${SDRROOT}/dev/devices/*"
