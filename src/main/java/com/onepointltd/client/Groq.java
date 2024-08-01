package com.onepointltd.client;

import com.onepointltd.config.Config;

public class Groq extends AbstractClient {

  private static final String GROQ_ENDPOINT = "https://api.groq.com/openai/v1/chat/completions";

  public Groq(Config config) {
    super(config, GROQ_ENDPOINT);
  }


}
