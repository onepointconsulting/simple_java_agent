GRADLE_HOME=/mnt/c/development/tools/gradle-8.9
JAVA_HOME="/mnt/c/Program\ Files/Java/openlogic-openjdk-21.0.4+7-windows-x64/"
export PATH=$GRADLE_HOME/bin:$JAVA_HOME/bin:$PATH
./gradlew clean shadowJar
