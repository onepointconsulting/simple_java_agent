package com.onepointltd.agent;

import static com.onepointltd.config.Logging.logger;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.onepointltd.client.Client;
import com.onepointltd.client.Groq;
import com.onepointltd.client.OpenAI;
import com.onepointltd.config.Config;
import com.onepointltd.config.ModelProvider;

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
    AgentExecutor agentExecutor = AgentExecutorFactory.createDefaultAgentExecutor(config, client);
    String answer = agentExecutor.execute(question);
    printAnswer(answer);
    assertFailure(answer);
  }

  private static void assertFailure(String answer) {
    assertFalse(answer.toLowerCase().contains("failed"));
  }

  static void runAgentFunctional(String question, Client client, Config config) {
    AgentExecutor agentExecutor =
        FunctionalAgentExecutorFactory.createDefaultAgentExecutor(config, client);
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
