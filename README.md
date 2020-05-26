meta-REDHAWK-SDR
=================

Meta-REDHAWK-SDR is an actively-maintained set of [Yocto][1]/[Open-Embedded][2] recipes for the [REDHAWK SDR][3] framework, its dependencies, GPP, other example Devices, all shared libraries (softpkg), and all CPP Components (SSE or NEON required for DataConverter).

This repository, along with the base Yocto framework will enable you to build the REDHAWK SDR framework for any hardware platform in which a Board Support Package is available.  We at Geon have successfully used this layer on a variety of Zynq targets including:

 * [ZC706 Evaluation Board](http://geontech.com/analog-devices-fmcomms-via-yocto/)
 * [ZedBoard](https://youtu.be/pKpbkYB43js)
 * [MicroZed](https://youtu.be/QvCrXl2cxpY)
 * [Ettus Research E310](https://youtu.be/WSKZsSxtWsQ)
 * iVeia Atlas-I-Z7e
 * Several ZC702 -based designs
 * [UltraScale+ ZCU102, ZCU106, ZCU111](https://geontech.com/redhawk-on-a-xilinx-zcu111/)
 * [Altera Stratix 10](https://github.com/kraj/meta-altera/tree/thud)

 > Note: Stick to the Yocto release-named branches for latest support.  Older releases than rocko are not maintained; please contact us through our [website](http://www.geontech.com) if this is a need.

Is my hardware supported?
-------------------------
The Yocto website provides a list of [Official BSPs][4] which include common hardware platforms like the Raspberry Pi, BeagleBoard, BeagleBone, NUC, Intel Atom, etc.

There are plenty of BSPs floating around for other hardware platforms so do some searching before you write your own.

Use the Pyro branch/version as your starting point for searching since it is the most recent version of Yocto that Geon has actively tested this layer against.

Getting Started
----------------

### Learning By Example

The most straight-forward installation is to use Google's repo and a manifest.  Complete instructions and our manifest [can be found here](http://github.com/GeonTech/meta-redhawk-sdr-manifests).  The instructions amount to a handful of terminal commands that setup a known-good build as a starting point.

Alternatively, you can clone this layer into your own Yocto source tree:

    cd <your work director where other meta* are loaded>
    git clone git://github.com/geontech/meta-redhawk-sdr.git

Then edit your `build/conf/bblayers.conf` to include a reference to `meta-redhawk-sdr` at the end of the list.  See our `meta-redhawk-sdr/conf/bblayers.conf.sample` as an example.

Classes
-------

There are a number of package classes provided in this layer to facilitate deploying one's own SoftPkgs, Components, Devices, and Waveforms to the target's SDRROOT.  For items that can be compiled like SoftPkg, Components and Devices, the `redhawk-softpkg`, `redhawk-component`, and `redhawk-device` classes help simplify deploying each by applying common patches to the project ahead of compiling it for the target.

The REDHAWK Device class defines 2 optional packages: `NAME-node` and `NAME-init`.  The `NAME-node` package has a runtime dependency against the device package `NAME` and tries to collect the node definition named in the variable `RH_NODE_NAME` from `SDRROOT/dev/nodes`.  On the other hand, the `NODE-init` package has a runtime dependency against the `NAME-node` package since it will be deploying a script to run `nodeBooter ...` with that definition on start-up.  Therefore to install your device into the target image so that it automatically boots, you only need to add `NAME-init` to your image definition and ensure your device package installs the node definition.

SPD Patching
------------

The `scripts/spd_utility` has two purposes.  The first purpose is to take a `cpp` Component, SoftPkg, or Device that has a defined `x86_64` implementation and create a new implementation matching `cpp-PACKAGE_ARCH`.  The output directory, etc. are all changed to match so that the output product will overwrite the original implementation back at the Domain controller, i.e., the second purpose.  The second purpose is to take the output product's SPD and merge it with the Domain's SDRROOT.  Together with collecting the ComponentHost package, the Domain will be able to distribute your assets in a multi-architecture Domain.  See `scripts/spd_utility --help` for arguments.

 > NOTE: If you would like to disable this functionality for your package, include `NO_SPD_PATCH = "1"` in the recipe.

Combining Domain Assets
----------------

One of the important features of REDHAWK SDR is its ability to transparently distribute the assets of Waveforms across any supported Executable Device.  The assets that come with REDHAWK are generally configured for x86/x86_64 target architectures, whereas those produced by this layer will be for the `PACKAGE_ARCH` defined in each case (see SPD Patching, above).  If you would like to combine these cross-compiled assets with a standard domain, ensure your image definition inherits from `redhawk-image`, build it, and then check the output path:

```
TMPDIR/deploy/images/MACHINE/your-image-PV-sdrroot.tar.gz
```

This tarball includes an install script and the above `spd_utility` that will merge each of the Components and SoftPkg libraries included in your image (`IMAGE_NAME`).

Testing
-------

This layer includes some QA tests for installed assets and the core framework itself.  To use any of this, you must first ensure your build host is configured for testing using [Yocto's OEQA system](https://www.yoctoproject.org/docs/3.1/dev-manual/dev-manual.html#performing-automated-runtime-testing).

You can see the set of available test cases in `lib/oeqa/runtime/cases` (omniorb, redhawk, etc.).  Some tests will be skipped if the related package is not installed and cover things like verifying the domain started properly and matches the configured `REDHAWK_DOMAIN` variable.

Enabling the test suites via your `local.conf` looks like this:

```
IMAGE_CLASSES += "testimage"
TEST_SUITES = "${REDHAWK_TEST_SUITE}"
TEST_TARGET = "qemu"
TEST_QEMUPARAMS = "-m 4096 -smp 4"
```

 > NOTE 1: This specifies a QEMU target with 4 GB RAM, 4 Cores.  This is not a minimum for REDHAWK; it's an example.
 > NOTE 2: You could `_append` or `+=` the `TEST_SUITES` variable to run all default tests in addition to REDHAWK's if you wish.

The `redhawk-test-image` includes all components, softpkgs, a GPP, and all initialization scripts to be a functional, stand-alone REDHAWK system ready to run waveforms.  So with the above configuration made, you can run the high-level tests:

```
bitbake redhawk-test-image -c testimage
```

 > NOTE: The above `local.conf` changes are enough to expose these tests to your own image as well; the above use of `redhawk-test-image` is an example.

There are other options available to you in the Yocto OE Core `testimage.bbclass` for configuring QEMU or utilizing remote test machines.  Please refer to the Yocto user manual and that class for those options.

Additional Resources
--------------------

[Yocto Mega Manual][7] 

[Bitbake cheatsheet][8]

[1]: https://www.yoctoproject.org/  "Yocto Project Homepage"
[2]: http://www.openembedded.org/wiki/Main_Page  "Open-Embedded Project Homepage"
[3]: http://redhawksdr.org "REDHAWK Homepage"
[4]: https://www.yoctoproject.org/downloads/bsps?release=All&title= "Board Support Package List"
[5]: https://github.com/EttusResearch/meta-ettus "Ettus BSP"
[6]: http://www.yoctoproject.org/docs/current/yocto-project-qs/yocto-project-qs.html#packages "Required Packages"
[7]: http://www.yoctoproject.org/docs/latest/mega-manual/mega-manual.html "Yocto Mega Manual"
[8]: http://www.openembedded.org/wiki/Bitbake_cheat_sheet "Bitbake Cheat Sheet"

