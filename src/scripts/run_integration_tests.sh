#!/usr/bin/env bash

sbt ++$TRAVIS_SCALA_VERSION clean it:test
