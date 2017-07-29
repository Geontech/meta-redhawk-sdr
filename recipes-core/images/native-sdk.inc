TOOLCHAIN_HOST_TASK_append = " nativesdk-python-cheetah \
    nativesdk-python-netserver nativesdk-python-pprint \
    nativesdk-python-pickle nativesdk-python-shell \
    nativesdk-python-subprocess nativesdk-python-modules \
    nativesdk-orc nativesdk-swig nativesdk-python-distutils \
    nativesdk-python-xml nativesdk-python-compile \
    nativesdk-cmake nativesdk-python-mako \
    "

# Removed glib-2.0
CORE_IMAGE_EXTRA_INSTALL = "\
    alsa-utils \
    i2c-tools \
    devmem2 \
    screen \
    vim \
    vim-vimrc \
    git \
    swig \
    boost \
    cmake \
    gsl \
    python \
    python-cheetah \
    python-modules \
    python-argparse \
    python-distutils \
    python-numpy \
    htop \
    orc \
    libudev \
    iperf3 \
    openssh-sftp \
    openssh-sftp-server \
    fftw \
    "
