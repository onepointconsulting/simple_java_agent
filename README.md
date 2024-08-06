# Simple Java Agent

Simple Java agent with multiple tools, like DuckDuckGo, Wikipedia and a calculator which you can use to answer questions. There are two types of agents in the project:

- Plain Agent
- Function based Agent

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

## Configuration

You will need to set the Groq API or the OpenAI key, the model name and the timeout as environment variables:

```
MODEL_PROVIDER=<groq|openai>
API_KEY=your-api-key
MODEL_NAME=<model-name>
TIMEOUT=60 # in seconds, optional.
```

These are all environmeent variables:

- MODEL_PROVIDER: The model provider, which can be either `groq` or `openai`. If you do not specify it, it will default to `groq`.
- API_KEY: The Groq API or the OpenAI key.
- MODEL_NAME: The model name, like e.g. `llama-3.1-70b-versatile`. `gpt-4o`
- TIMEOUT: timeout for the model in seconds. Optional

The timeout is in seconds.


## Running the Agent

Please set the `JAVA_HOME` to Java 20 or higher and the Groq `API_KEY` environment variable and run the following command in the root folder after compiling the project:

```batch
.\run.bat -p "<Some question>"
```

```bash
.\run.bat "<Some question>"
```

Examples:

```batch
.\run.bat -p "Who is the current UK Prime Minister?"
```

```bash
./run.sh "Who is Simone Biles?"
```

Note that for Linux you only send the prompt as argument to the shell script.

The answer should show up in the console:

```
*****************************
Answer: The current UK Prime Minister is Keir Starmer.
*****************************
```

```batch
.\run.bat -p "Who is the current UK Chancellor of the Exchequer?"
```

### Parameters which you can use to run the agent main application

```
usage: parameters: [-c] [-m <max-iterations>] [-p <prompt>] [-t
       <agent-type>]
 -c,--print-config                      prints the configuration
 -m,--max-iterations <max-iterations>   sets the maximum number of
                                        iterations, like e.g. 5
 -p,--prompt <prompt>                   set the question you want to ask
                                        in quotes
 -t,--agent-type <agent-type>           sets the agent type. One of
                                        'plain', 'function' are the
                                        options
"%JAVA_HOME%\bin\java" -jar simple_agent-1.0-SNAPSHOT.jar -p "Who is the
UK prime minister?" -t plain -m 6

```