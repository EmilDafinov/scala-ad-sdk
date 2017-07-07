#!/usr/bin/env bash
echo "Tags on the current commit: "
git tag --points-at HEAD
# Output current project version:
sbt version
# Errors out if the dynamically generated project version (based on the git version) is manually overriden sbt 
sbt dynverAssertVersion
