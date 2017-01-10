meta-REDHAWK-SDR
=================

> **Note:** This is a distant fork of [Axios' layer](http://github.com/Axios-Engineering/openembedded-hawk) that was fairly far removed.  Their copyright has been added to this copyright list along various recipes and pieces of their README.  After a series of updates and changes it was different enough that it was pushed as a separate layer.

What is Yocto/Open-Embedded?
---------------------------
From the [Yocto][1] page itself:


>It's a complete embedded Linux development environment with tools, metadata, and documentation - everything you need. The free tools are easy to get started with, powerful to work with (including emulation environments, debuggers, an Application Toolkit Generator, etc.) and they allow projects to be carried forward over time without causing you to lose optimizations and investments made during the project’s prototype phase.

In short, [Yocto][1] allows you to build a custom, light weight embedded linux distribution  built specifically for your hardware. 

What is Meta-REDHAWK-SDR
--------------------------
Meta-REDHAWK-SDR is a set of [Yocto][1]/[Open-Embedded][2] recipes for the [REDHAWK][3] framework, its dependencies, and a handful of example components, devices, and shared libraries.

This repository, along with the base Yocto framework will enable you to build the REDHAWK framework for any hardware platform in which a Board Support Package is available. 

Is my hardware supported?
-------------------------
The Yocto website provides a list of [Official BSPs][4] which include common hardware platforms like the Raspberry Pi, BeagleBoard, BeagleBone, NUC, Intel Atom, etc.

There are plenty of BSPs floating around for other hardware platforms so do some searching before you write your own.

Getting Started
----------------

The most straight-forward installation is to use Google's repo and our [manifest](http://github.com/GeonTech/meta-redhawk-sdr-manifests).  It amounts to a handful of terminal commands to `repo init` a manifest, which downloads the layers for a given configuration.  Then running a terminal command to use our bblayers and local configuration files as templates.  And finally, running bitbake.  Instructions are provided at the link above.

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

[OpenEmbedded-Hawk][9]

[1]: https://www.yoctoproject.org/  "Yocto Project Homepage"
[2]: http://www.openembedded.org/wiki/Main_Page  "Open-Embedded Project Homepage"
[3]: http://redhawksdr.org "REDHAWK Homepage"
[4]: https://www.yoctoproject.org/downloads/bsps?release=All&title= "Board Support Package List"
[5]: https://github.com/EttusResearch/meta-ettus "Ettus BSP"
[6]: http://www.yoctoproject.org/docs/current/yocto-project-qs/yocto-project-qs.html#packages "Required Packages"
[7]: http://www.yoctoproject.org/docs/latest/mega-manual/mega-manual.html "Yocto Mega Manual"
[8]: http://www.openembedded.org/wiki/Bitbake_cheat_sheet "Bitbake Cheat Sheet"
[9]: http://github.com/axios-engineering/openembedded-hawk "Axios' OpenEmbedded-Hawk"

