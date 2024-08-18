package com.onepointltd.tools;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.onepointltd.config.Config;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class SerpAPIToolTest {

  @Test
  @Disabled
  void whenExecute_shouldReturnResults() {
    var config = new Config();
    var serpAPITool = new SerpAPITool(config);
    assumeTrue(config.getSerpApiKey() != null);
    assumeTrue(config.getSerpApiLocation() != null);
    assumeTrue(config.getSerpApiGeoLocation() != null);
    assumeTrue(config.getSerpApiLanguageCode() != null);
    var searchResult = serpAPITool.execute("weather tomorrow in London");
    assertNotNull(searchResult);
  }
}
