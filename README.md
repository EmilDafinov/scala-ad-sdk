
[![Build Status](https://travis-ci.org/EmilDafinov/scala-ad-sdk.svg?branch=master)](https://travis-ci.org/EmilDafinov/scala-ad-sdk)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/4b72b43682c44efb9e6da00d60b15337)](https://www.codacy.com/app/EmilDafinov/scala-ad-sdk?utm_source=github.com&utm_medium=referral&utm_content=EmilDafinov/scala-ad-sdk&utm_campaign=Badge_Coverage)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4b72b43682c44efb9e6da00d60b15337)](https://www.codacy.com/app/EmilDafinov/scala-ad-sdk?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=EmilDafinov/scala-ad-sdk&amp;utm_campaign=Badge_Grade)

[ ![Download](https://api.bintray.com/packages/emildafinov/maven/scala-ad-sdk/images/download.svg) ](https://bintray.com/emildafinov/maven/scala-ad-sdk/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.emildafinov/scala-ad-sdk_2.12/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.emildafinov/scala-ad-sdk_2.12)

[![Join the chat at https://gitter.im/scala-ad-sdk/Lobby](https://badges.gitter.im/scala-ad-sdk/Lobby.svg)](https://gitter.im/scala-ad-sdk/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)


# scala-ad-sdk

## Release process

### On version numbers
This project uses the [sbt-dynver](https://github.com/dwijnand/sbt-dynver) plugin in order to generate its version
number. See the plugin documentation for more details.
Our behavior for snapshot versions is slightly different than the plugin default however: in particular, commits that 
are not tagged with a version tag are considered snapshots. Moreover, snapshot versions have '-SNAPSHOT' appended to 
their sbt-dynver generated version number, so that they can be pushed to oss.jfrog.org.

### Publishing artifacts
Every time the build runs successfully, it uploads the created project binaries
* In case `isSnapshot` is true, the build is a snapshot (development) build. 
The build artifacts will be uploaded to `oss.jfrog.org`
* In case `isSnapshot` is true, then the build is a release version.
The build artifacts will be uploaded to Bintray, with the release version matching the project version

### Creating a release
We always create the next release version from the current commit on master.

* Make sure the commit you want to release is pushed to the upstream repo
* Start by creating a new version tag for the current commit locally
	`git tag -a v[my-release-number-here] -m "my tag message"`
* Push your newly created tag to the upstream repo
