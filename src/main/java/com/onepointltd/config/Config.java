package com.onepointltd.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {

  public static final String API_KEY_GROQ = "API_KEY_GROQ";

  public static final String API_KEY_OPENAI = "API_KEY_OPENAI";

  public static final String MODEL_NAME_GROQ = "MODEL_NAME_GROQ";

  public static final String MODEL_NAME_OPENAI = "MODEL_NAME_OPENAI";

  public static final String TIMEOUT = "TIMEOUT";

  public static final String MODEL_PROVIDER = "MODEL_PROVIDER";

  public static final String SERP_API_KEY = "SERP_API_KEY";

  public static final String SERP_API_LOCATION = "SERP_API_LOCATION";

  public static final String SERP_API_LANGUAGE_CODE = "SERP_API_LANGUAGE_CODE";

  public static final String SERP_API_GEO_LOCATION = "SERP_API_GEO_LOCATION";

  public static final String AGENT_MAX_ITERATIONS = "AGENT_MAX_ITERATIONS";

  private final String apiKeyGroq;

  private final String apiKeyOpenAI;

  private final String modelNameGroq;

  private final String modelNameOpenAI;

  private final int timeout;

  private final String serpApiKey;

  private final String serpApiLocation;

  private final String serpApiLanguageCode;

  private final String serpApiGeoLocation;

  private final int agentMaxIterations;

  private ModelProvider provider;

  public Config() {
    Dotenv dotenv = Dotenv.load();
    apiKeyGroq = dotenv.get(API_KEY_GROQ);
    checkExists(apiKeyGroq, API_KEY_GROQ);
    apiKeyOpenAI = dotenv.get(API_KEY_OPENAI);
    checkExists(apiKeyOpenAI, API_KEY_OPENAI);
    modelNameGroq = dotenv.get(MODEL_NAME_GROQ);
    checkExists(modelNameGroq, MODEL_NAME_GROQ);
    modelNameOpenAI = dotenv.get(MODEL_NAME_OPENAI);
    checkExists(modelNameOpenAI, MODEL_NAME_OPENAI);
    String timeoutStr = dotenv.get(TIMEOUT);
    timeout = Integer.parseInt(timeoutStr == null ? "60" : timeoutStr);
    String providerVar = dotenv.get(MODEL_PROVIDER);
    if (providerVar == null) {
      provider = ModelProvider.GROQ;
    } else {
      provider = ModelProvider.valueOf(providerVar.toUpperCase());
    }
    serpApiKey = dotenv.get(SERP_API_KEY); // Might be null
    serpApiLocation = dotenv.get(SERP_API_LOCATION); // Might be null
    serpApiLanguageCode = dotenv.get(SERP_API_LANGUAGE_CODE); // Might be null
    serpApiGeoLocation = dotenv.get(SERP_API_GEO_LOCATION); // Might be null

    String agentMaxIterationsStr = dotenv.get(AGENT_MAX_ITERATIONS, "10");
    agentMaxIterations = Integer.parseInt(agentMaxIterationsStr);
  }

  private <T> void checkExists(T value, String varname) {
    if (value == null) {
      throw new IllegalArgumentException(
          String.format("%s environment variable is not set", varname));
    }
  }

  public String getApiKey() {
    return provider == ModelProvider.GROQ ? apiKeyGroq : apiKeyOpenAI;
  }

  public String getModelName() {
    return provider == ModelProvider.GROQ ? modelNameGroq : modelNameOpenAI;
  }

  public int getTimeout() {
    return timeout;
  }

  public ModelProvider getProvider() {
    return provider;
  }

  public void setProvider(ModelProvider provider) {
    this.provider = provider;
  }

  public String getSerpApiKey() {
    return serpApiKey;
  }

  public String getSerpApiLocation() {
    return serpApiLocation;
  }

  public String getSerpApiLanguageCode() {
    return serpApiLanguageCode;
  }

  public String getSerpApiGeoLocation() {
    return serpApiGeoLocation;
  }

  public int getAgentMaxIterations() {
    return agentMaxIterations;
  }

  @Override
  public String toString() {
    return String.format(
        """
MODEL_PROVIDER: %s
API_KEY: %s
MODEL_NAME: %s
TIMEOUT: %d
""",
        provider, getApiKey(), getModelName(), timeout);
  }
}
