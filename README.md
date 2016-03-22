meta-REDHAWK-SDR
=================

> **Note:** This is a distant fork of [Axios' layer](http://github.com/Axios-Engineering/openembedded-hawk) that was fairly far removed.  Their copyright has been added to this copyright list along with a large portion of the original README, and some recipes.  After a series of updates and changes it was different enough that it was pushed as a separate layer.

What is Yocto/Open-Embedded?
---------------------------
From the [Yocto][1] page itself:


>It's a complete embedded Linux development environment with tools, metadata, and documentation - everything you need. The free tools are easy to get started with, powerful to work with (including emulation environments, debuggers, an Application Toolkit Generator, etc.) and they allow projects to be carried forward over time without causing you to lose optimizations and investments made during the project’s prototype phase.

In short, [Yocto][1] allows you to build a custom, light weight embedded linux distribution  built specifically for your hardware. 

What is Openembedded-Hawk
--------------------------
Openembedded-hawk is a set of [Yocto][1]/[Open-Embedded][2] recipes for the [REDHAWK][3] framework, its dependencies, and a handful of example components, devices, and shared libraries.

This repository, along with the base Yocto framework will enable you to build the REDHAWK framework for any hardware platform in which a Board Support Package is available. 

Is my hardware supported?
-------------------------
The Yocto website provides a list of [Official BSPs][4] which include common hardware platforms like the Raspberry Pi, BeagleBoard, BeagleBone, NUC, Intel Atom, etc.

There are plenty of BSPs floating around for other hardware platforms so do some searching before you write your own.

Getting Started
----------------

1. Install the required software listed in the [Yocto Quick Start Packages Section][6]

2. Checkout the Yocto Poky repositories dizzy branch:

    git clone -b dizzy git://git.yoctoproject.org/poky.git


3. Clone the openembedded-hawk repository inside the poky folder as meta-redhawk.

    ```
    cd poky
    git clone https://<server>/openembedded/meta-redhawk-sdr.git meta-redhawk
    ```

4. Source the build-env script

    ```
    source oe-init-build-env
    ```

5. Pick a machine type within the conf/local.conf file. The default will build for an emulated x86 machine type.

6. Add REDHAWK recipes to the build image. The easiest way to do this is by using the conf/local.conf file and adding the IMAGE_INSTALL_append variable at the end. Here is an example that adds the core framework, frontend, and a GPP. Note that all the dependencies will automatically be built including redhawk-core

    ```
    IMAGE_INSTALL_append = " redhawk-frontend redhawk-gpp"
    ```

6. Add the meta-redhawk directory to the BBLAYERS variable in conf/bblayers.conf so yocto knows where to search for our custom recipes.

7. Build an image:

    ```
    bitbake core-image-minimal
    ```

Alternative Methods
-------------------

In the `contrib/scripts` folder is the `build-image.sh` script, a derivative of a script from Philip Ballister of Ettus Research who included it with their `meta-sdr`.  It uses `wic` to build a single image file that can be directly copied to an SD card (`dd`) resulting in the appropriate partitions, etc., buased on the associated `wks` file.  Specify the `BUILD_IMAGE` and `MACHINE` environment variables and this script will go through the whole process for you, automated.

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
