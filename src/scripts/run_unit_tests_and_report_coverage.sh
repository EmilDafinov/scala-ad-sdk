#!/usr/bin/env bash

sbt ++$TRAVIS_SCALA_VERSION clean coverage test coverageReport coverageAggregate codacyCoverage
