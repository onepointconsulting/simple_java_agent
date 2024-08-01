package com.onepointltd.aagent;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.agent.FunctionalAgentExecutor;
import com.onepointltd.client.Client;
import com.onepointltd.client.Groq;
import com.onepointltd.client.OpenAI;
import com.onepointltd.config.Config;
import com.onepointltd.config.ModelProvider;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalTool;
import com.onepointltd.tools.FunctionalWikipedia;

public class AgentRunnerFunction {

  static void rungAgentGroq(String question) {
    Config config = new Config();
    System.out.printf("Model: %s%n", config.getModelName());
    runAgent(question, new Groq(new Config()), config);
  }

  static void rungAgentOpenAI(String question) {
    Config config = new Config();
    config.setProvider(ModelProvider.OPENAI);
    config.setModelName("gpt-4o");
    System.out.printf("Model: %s%n", config.getModelName());
    runAgent(question, new OpenAI(config), config);
  }

  static void runAgent(String question, Client client, Config config) {
    AgentExecutor agentExecutor = new FunctionalAgentExecutor(client,
        new FunctionalTool[]{new FunctionalDuckDuckGo(config), new FunctionalWikipedia(config), new FunctionalCalculator()}, 8);
    String answer = agentExecutor.execute(question);
    System.out.println("*****************************");
    System.out.println(answer);
    System.out.println("*****************************");
  }
}
