package com.onepointltd.aagent;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.agent.FunctionalAgentExecutor;
import com.onepointltd.client.Client;
import com.onepointltd.client.Groq;
import com.onepointltd.client.OpenAI;
import com.onepointltd.config.Config;
import com.onepointltd.config.ModelProvider;
import com.onepointltd.tools.Calculator;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalTool;
import com.onepointltd.tools.FunctionalWikipedia;
import com.onepointltd.tools.TodayTool;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;

public class AgentRunnerFunction {

  static void rungAgentGroqSimple(String question) {
    rungAgentGroq(question, false);
  }

  static void rungAgentGroqFunctional(String question) {
    rungAgentGroq(question, true);
  }

  static void rungAgentGroq(String question, boolean functional) {
    Config config = new Config();
    System.out.printf("Model: %s%n", config.getModelName());
    if (functional) {
      runAgentFunctional(question, new Groq(new Config()), config);
    } else {
      runAgent(question, new Groq(new Config()), config);
    }
  }

  static void rungAgentOpenAI(String question) {
    Config config = new Config();
    config.setProvider(ModelProvider.OPENAI);
    config.setModelName("gpt-4o");
    System.out.printf("Model: %s%n", config.getModelName());
    runAgentFunctional(question, new OpenAI(config), config);
  }

  static void runAgent(String question, Client client, Config config) {
    AgentExecutor agentExecutor =
        new AgentExecutor(
            client,
            new Tool[] {
              new DuckDuckGo(config), new Wikipedia(config), new Calculator(), new TodayTool()
            },
            8);
    String answer = agentExecutor.execute(question);
    printAnswer(answer);
  }

  static void runAgentFunctional(String question, Client client, Config config) {
    AgentExecutor agentExecutor =
        new FunctionalAgentExecutor(
            client,
            new FunctionalTool[] {
              new FunctionalDuckDuckGo(config),
              new FunctionalWikipedia(config),
              new FunctionalCalculator()
            },
            8);
    String answer = agentExecutor.execute(question);
    printAnswer(answer);
  }

  private static void printAnswer(String answer) {
    System.out.println("*****************************");
    System.out.println(answer);
    System.out.println("*****************************");
  }
}
