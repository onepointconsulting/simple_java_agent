package com.onepointltd.config;

public class Config {

  public static final String API_KEY = "API_KEY";

  public static final String MODEL_NAME = "MODEL_NAME";

  public static final String TIMEOUT = "TIMEOUT";

  public static final String MODEL_PROVIDER = "MODEL_PROVIDER";

  private final String apiKey;

  private String modelName;

  private final int timeout;

  private ModelProvider provider;

  public Config() {
    apiKey = System.getenv(API_KEY);
    checkExists(apiKey, API_KEY);
    modelName = System.getenv(MODEL_NAME);
    checkExists(modelName, MODEL_NAME);
    String timeoutStr = System.getenv(TIMEOUT);
    timeout = Integer.parseInt(timeoutStr == null ? "60" : timeoutStr);
    String providerVar = System.getenv(MODEL_PROVIDER);
    if (providerVar == null) {
      provider = ModelProvider.GROQ;
    } else {
      provider = ModelProvider.valueOf(providerVar.toUpperCase());
    }
  }

  private <T> void checkExists(T value, String varname) {
    if (value == null) {
      throw new IllegalArgumentException(
          String.format("%s environment variable is not set", varname));
    }
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getModelName() {
    return modelName;
  }

  public int getTimeout() {
    return timeout;
  }

  public ModelProvider getProvider() {
    return provider;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public void setProvider(ModelProvider provider) {
    this.provider = provider;
  }
  @Override
  public String toString() {
    return String.format("""
MODEL_PROVIDER: %s
API_KEY: %s
MODEL_NAME: %s
TIMEOUT: %d
""", provider, apiKey, modelName, timeout);
  }
}
