#!/usr/bin/env bash

# Write the bintray credentials to disk (sbt-bintray expects them to be present there in order to run)
curl -O https://gist.githubusercontent.com/EmilDafinov/c874e7f3abe77dd770c2bd26828b9224/raw/f75b659d1a2c3634140eace2c7899dc634e2790b/travis_bintray_credentials.sh &&
chmod +x travis_bintray_credentials.sh &&
./travis_bintray_credentials.sh &&

#Write the Github credentials to disk
echo "oauth = ${GITHUB_RELEASE_TOKEN}" > $HOME/.github

sbt ++$TRAVIS_SCALA_VERSION bintrayEnsureCredentials publish publishReleaseNotes bintraySyncMavenCentral
