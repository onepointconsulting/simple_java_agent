package com.onepointltd.config;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConfigTest {

  @Test
  void whenEnvSetup_ShouldPass() {
    var config = new Config();
    assertNotEquals("", config.getApiKey());
    assertNotEquals("", config.getModelName());
    assertTrue(
        config.getProvider() == ModelProvider.GROQ || config.getProvider() == ModelProvider.OPENAI);
  }
}
