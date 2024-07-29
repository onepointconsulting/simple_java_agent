# Simple Java Agent

Simple framework written in Java to create Java AI Agents.

## Build

```bash
gradle build
```

## Creating Fat Jar

On Windows you can run the following script:

```batch
.\build.bat
```

You will need to set the JAVA_HOME to Java 20 or higher and run the `gradle jar` command.

```
set JAVA_HOME=C:\Program Files\Java\jdk-20
call gradlew jar
```

## Running the Agent

Please set the `JAVA_HOME` to Java 20 or higher and the Groq `API_KEY` environment variable and run the following command in the root folder after compiling the project:

```batch
.\run.bat "<Some question>"
```

Example:

```batch
.\run.bat "Who is the current UK Prime Minister?"
```

The answer should show up in the console:

```
*****************************
Answer: The current UK Prime Minister is Keir Starmer.
*****************************
```