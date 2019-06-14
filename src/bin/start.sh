#!/bin/sh

cd `dirname $0`

# Environment variable JAVA_HOME is required
if [ -z "$JAVA_HOME" ]; then
    echo "Error: $JAVA_HOME has not been set"
    exit 1
fi

JAVA_CMD="$JAVA_HOME/bin/java"

# Java must be installed
if [ ! -f "$JAVA_CMD" ]; then
    echo "Error: $JAVA_CMD was not found"
    exit 1
fi

# Use JRE_OPTS to specify options to the JVM
# JRE_OPTS=

exec "$JAVA_CMD" $JRE_OPTS -jar application.jar