SRC_URI = "http://downloads.sourceforge.net/omniorb/omniORBpy-4.2.0.tar.bz2;name=omniORBpytarbz2"
SRC_URI_virtclass-native = "http://downloads.sourceforge.net/omniorb/omniORBpy-4.2.0.tar.bz2;name=omniORBpytarbz2"

SRC_URI[omniORBpytarbz2.md5sum] = "50ecde547c865aad2074d30224779412"
SRC_URI[omniORBpytarbz2.sha256sum] = "c82b3bafacbb93cfaace41765219155f2b24eb3781369bba0581feb1dc50fe5e"

PREFERRED_VERSION_omniorb = "4.2.0"

SRC_URI_append = " file://omniORBpy_modules_ziop_dir.mk-4.2.0.patch"

include omniorbpy.inc
