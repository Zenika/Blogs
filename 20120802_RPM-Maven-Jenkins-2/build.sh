#!/bin/sh

set -e

if [ -z "$WORKSPACE" ] ; then
	case $0 in
		/*)
			WORKSPACE="`dirname $0`"
			;;
		*)
			WORKSPACE="`pwd`/`dirname $0`"
			;;
	esac
fi

rpmbuild -bb $WORKSPACE/sirkuttaa.spec \
	--define "_builddir $WORKSPACE" \
	--define "_rpmdir   $WORKSPACE/target" \
	--define "build_id  ${BUILD_ID:-SNAPSHOT}"

