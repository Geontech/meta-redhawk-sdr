SRC_URI = "http://downloads.sourceforge.net/omniorb/omniORBpy-3.6.tar.bz2;name=omniORBpytarbz2"
SRC_URI_virtclass-native = "http://downloads.sourceforge.net/omniorb/omniORBpy-3.6.tar.bz2;name=omniORBpytarbz2"

SRC_URI[omniORBpytarbz2.md5sum] = "a7ab4789b913313f18a1171ff7a140b7"
SRC_URI[omniORBpytarbz2.sha256sum] = "b845eef13b56dfb47c98d65636ede5155ed4b84b2a64e35831b94668878af7c7"

PREFERRED_VERSION_omniorb = "4.1.6"

include omniorbpy.inc

