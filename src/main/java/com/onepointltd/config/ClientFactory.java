package com.onepointltd.config;

import com.onepointltd.client.Client;
import com.onepointltd.client.Groq;
import com.onepointltd.client.OpenAI;

public class ClientFactory {

  public static Client createClient(Config config) {
    return config.getProvider() == ModelProvider.OPENAI ? new OpenAI(config) : new Groq(config);
  }
}
