# export API_KEY=<API key>
export MODEL_NAME=llama-3.1-70b-versatile
export TIMEOUT=60
export MODEL_PROVIDER=GROQ
java -jar ./build/libs/simple_agent-1.0-SNAPSHOT.jar -p "$1"