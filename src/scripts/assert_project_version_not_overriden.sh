#!/usr/bin/env bash
# Errors out if the dynamically generated project version (based on the git version) is manually overriden sbt 
sbt dynverAssertVersion
