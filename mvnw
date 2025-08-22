#!/usr/bin/env sh
# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup script (Unix)
# ----------------------------------------------------------------------------
set -e

PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \\(.*\\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/$link"
  fi
done
BASEDIR=`dirname "$PRG"`
BASEDIR=`cd "$BASEDIR" && pwd`
WRAPPER_DIR="$BASEDIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
WRAPPER_PROPERTIES="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$WRAPPER_PROPERTIES" ]; then
  echo "ERROR: $WRAPPER_PROPERTIES not found" >&2
  exit 1
fi

WRAP_URL=`grep -E "^wrapperUrl=" "$WRAPPER_PROPERTIES" | cut -d'=' -f2-`

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Downloading maven-wrapper.jar..."
  curl -fsSL "$WRAP_URL" -o "$WRAPPER_JAR" || { echo "Download failed" >&2; exit 1; }
fi

JAVA_EXE=java
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  JAVA_EXE="$JAVA_HOME/bin/java"
fi

exec "$JAVA_EXE" $MAVEN_OPTS -classpath "$WRAPPER_JAR" -Dmaven.multiModuleProjectDirectory="$BASEDIR" org.apache.maven.wrapper.MavenWrapperMain "$@"
