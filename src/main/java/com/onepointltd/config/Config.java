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
    timeout = Integer.parseInt(System.getenv(TIMEOUT));
    checkExists(timeout, TIMEOUT);
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
}
