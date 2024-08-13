package com.onepointltd.agent;

import static com.onepointltd.config.Logging.logger;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.onepointltd.client.Client;
import com.onepointltd.client.Groq;
import com.onepointltd.client.OpenAI;
import com.onepointltd.config.Config;
import com.onepointltd.config.ModelProvider;
import com.onepointltd.tools.Calculator;
import com.onepointltd.tools.DateFromTodayTool;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDateFromTodayTool;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalTodayTool;
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
    config.setProvider(ModelProvider.GROQ);
    logger.info(String.format("Model: %s%n", config.getModelName()));
    if (functional) {
      runAgentFunctional(question, new Groq(config), config);
    } else {
      runAgent(question, new Groq(config), config);
    }
  }

  static void rungAgentOpenAISimple(String question) {
    rungAgentOpenAI(question, false);
  }

  static void rungAgentOpenAIFunctional(String question) {
    rungAgentOpenAI(question, true);
  }

  static void rungAgentOpenAI(String question, boolean functional) {
    Config config = new Config();
    config.setProvider(ModelProvider.OPENAI);
    System.out.printf("Model: %s%n", config.getModelName());
    if (functional) {
      runAgentFunctional(question, new OpenAI(config), config);
    } else {
      runAgent(question, new OpenAI(config), config);
    }
  }

  static void runAgent(String question, Client client, Config config) {
    AgentExecutor agentExecutor =
        new AgentExecutor(
            client,
            new Tool[] {
              new DuckDuckGo(config),
              new Wikipedia(config),
              new Calculator(),
              new TodayTool(),
              new DateFromTodayTool()
            },
            8);
    String answer = agentExecutor.execute(question);
    printAnswer(answer);
    assertFailure(answer);
  }

  private static void assertFailure(String answer) {
    assertFalse(answer.toLowerCase().contains("failed"));
  }

  static void runAgentFunctional(String question, Client client, Config config) {
    AgentExecutor agentExecutor =
        new FunctionalAgentExecutor(
            client,
            new FunctionalTool[] {
              new FunctionalDuckDuckGo(config),
              new FunctionalWikipedia(config),
              new FunctionalCalculator(),
              new FunctionalTodayTool(),
              new FunctionalDateFromTodayTool()
            },
            8);
    String answer = agentExecutor.execute(question);
    printAnswer(answer);
    assertFailure(answer);
  }

  private static void printAnswer(String answer) {
    System.out.println("*****************************");
    System.out.println(answer);
    System.out.println("*****************************");
  }
}
