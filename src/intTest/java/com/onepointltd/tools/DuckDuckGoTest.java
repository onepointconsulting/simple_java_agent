package com.onepointltd.tools;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.onepointltd.config.Config;
import org.junit.jupiter.api.Test;

public class DuckDuckGoTest {

  private final Config config = new Config();
  private final DuckDuckGo duckDuckGo = new DuckDuckGo(config);

  @Test
  public void whenSendSearch_ShouldReceiveResponse() {
    String response = duckDuckGo.execute("American election 2024");
    assertNotNull(response);
    System.out.printf("Response: %s%n", response);
  }
}
