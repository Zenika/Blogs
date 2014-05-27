#!/bin/sh

mvn eclipse:clean eclipse:eclipse clean compile -DdownloadSources=true -DdownloadJavadocs=true $@

