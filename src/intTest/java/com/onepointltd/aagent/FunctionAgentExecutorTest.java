package com.onepointltd.aagent;

import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.agent.FunctionalAgentExecutor;
import com.onepointltd.client.Groq;
import com.onepointltd.config.Config;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalTool;
import com.onepointltd.tools.FunctionalWikipedia;
import org.junit.jupiter.api.Test;

public class FunctionAgentExecutorTest {

  @Test
  public void whenOlympicGames_ShouldBeParis() {
    runAgent(QUESTION_OLYMPIC);
  }

  private static void runAgent(String question) {
    Config config = new Config();
    System.out.printf("Model: %s%n", config.getModelName());
    AgentExecutor agentExecutor = new FunctionalAgentExecutor(new Groq(config),
        new FunctionalTool[]{new FunctionalDuckDuckGo(config), new FunctionalWikipedia(config), new FunctionalCalculator()}, 5);
    String answer = agentExecutor.execute(question);
    System.out.println("*****************************");
    System.out.println(answer);
    System.out.println("*****************************");
  }
}
