package com.onepointltd.client;

import com.onepointltd.config.Config;

public class OpenAI extends AbstractClient {

  private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/chat/completions";

  public OpenAI(Config config) {
    super(config, OPENAI_ENDPOINT);
  }
}
