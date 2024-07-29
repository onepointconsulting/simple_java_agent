package com.onepointltd.config;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ConfigTest {

  @Test
  void whenEnvSetup_ShouldPass() {
    var config = new Config();
    assertNotEquals("", config.getApiKey());
  }
}
