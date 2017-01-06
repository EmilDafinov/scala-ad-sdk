# WebAppBase

[![Build Status](https://travis-ci.org/emiliorodo/AD_Akka_Http.svg?branch=master)](https://travis-ci.org/emiliorodo/AD_Akka_Http)

Creating a sample full stack web-app using Scala

Requires Java 8

# Development environment


## Building the application

This project is built and packaged using SBT.
You can install sbt with your favorite package manager, under OS X
I would recommend Homebrew

```
brew install sbt
```
If you are not using OS X, use whichever is the most popular package
manager for your UNIX distribution (apt-get for Debian/Ubuntu, yum for CentOS/Fedora/RedHat, etc)

The build is configured in the ```build.sbt``` file found in the root
directory of the project

## Packaging

The application is using sbt native packager to produce a docker image of the application
Use
```
sbt docker:publishLocal
```
to generate the image

## Configuration

The application configuration can be found in 
```
./src/main/resources/application.conf
```
Many aspects of the application can be configured there. 
Read the comments in that file for more information.

## UI

Currently none. However, if you need to add it, the server is would serve
any static resources in a subdirectory of the resources folder.
The relative path to that directory can be configured using the 
```
webapp.root.directory
```
key in ```application.conf```

## Tools


The [SBT-revolver](https://github.com/spray/sbt-revolver) plugin is enabled in the project, 
which allows you to have SBT re-compile and re-start your application when a change
in the sources is detected
