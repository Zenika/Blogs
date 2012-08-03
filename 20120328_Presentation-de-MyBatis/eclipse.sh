#!/bin/sh

mvn eclipse:clean eclipse:eclipse -DdownloadSources=true -DdownloadJavadocs=true clean compile $@
