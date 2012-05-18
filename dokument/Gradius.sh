#!/bin/bash
#
# Requires git, ant and that the tools and platform-tools 
# folder in the Android SDK is on your path.
#
# Retrieve the latest version from your repository﻿
#
git clone git@github.com:jasinski/gradiuss.git
#
# Generate build.xml
#
cd gradiuss/Gradius/
android update project --path .
cd ..
android update test-project -m ../Gradius -p TestGradius/
#
# Compile, sign with debug key install to emulator/device and
# run tests.
#
cd TestGradius
ant test
#
