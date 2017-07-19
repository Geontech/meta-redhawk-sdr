meta-REDHAWK-SDR
=================

Meta-REDHAWK-SDR is an actively-maintained set of [Yocto][1]/[Open-Embedded][2] recipes for the [REDHAWK SDR][3] framework, its dependencies, and a handful of example Devices and shared libraries.

This repository, along with the base Yocto framework will enable you to build the REDHAWK SDR framework for any hardware platform in which a Board Support Package is available.  We at Geon have successfully used this layer on a variety of Zynq targets including:

 * [ZC706 Evaluation Board](http://geontech.com/analog-devices-fmcomms-via-yocto/)
 * [ZedBoard](https://youtu.be/pKpbkYB43js)
 * [MicroZed](https://youtu.be/QvCrXl2cxpY)
 * [Ettus Research E310](https://youtu.be/WSKZsSxtWsQ)
 * iVeia Atlas-I-Z7e
 * Several ZC702 -based designs

 > Note: Only the presently-tagged version is supported by this repository, however we can support some earlier versions if required.  Please contact us through our [website](http://www.geontech.com) if this is a need.

Is my hardware supported?
-------------------------
The Yocto website provides a list of [Official BSPs][4] which include common hardware platforms like the Raspberry Pi, BeagleBoard, BeagleBone, NUC, Intel Atom, etc.

There are plenty of BSPs floating around for other hardware platforms so do some searching before you write your own.

Use the Krogoth branch/version as your starting point for searching since it is the most recent version of Yocto that Geon has actively tested this layer against.

Getting Started
----------------

### Learning By Example

The most straight-forward installation is to use Google's repo and a manifest.  Complete instructions and our manifest [can be found here](http://github.com/GeonTech/meta-redhawk-sdr-manifests).  The instructions amount to a handful of terminal commands that setup a known-good build as a starting point.

Alternatively, you can clone this layer into your own Yocto source tree:

    cd <your work director where other meta* are loaded>
    git clone git://github.com/geontech/meta-redhawk-sdr.git

Then edit your `build/conf/bblayers.conf` to include a reference to `meta-redhawk-sdr` at the end of the list.  See our `meta-redhawk-sdr/conf/bblayers.conf.sample` as an example.

Finishing the Build
-------------------

In the `contrib/scripts` folder is the `build-image.sh` script, a derivative of a script from Philip Balister (@balister) of Ettus Research who included it with their `meta-sdr`.  The script uses `wic` to build a single image file that can be directly copied to an SD card (`dd`) resulting in the appropriate partitions, etc., based on the associated `wks` file.  

To use it, link this script into your `build` directory, set it to executable, and specify the `BUILD_IMAGE` and `MACHINE` environment variables (e.g., `qemuarm` and `redhawk-gpp-image`).  Then running this script will go through the whole bitbake process for you, automated.

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

