package com.onepointltd.aagent;

import static com.onepointltd.provider.QuestionProvider.QUESTION_MATH_EQUATION;
import static com.onepointltd.provider.QuestionProvider.QUESTION_NVIDIA_SHARE_PRICES;
import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;
import static com.onepointltd.provider.QuestionProvider.QUESTION_SQUARE_ROOT;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WEATHER_LONDON_TOMORROW;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010;

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

  @Test
  public void whenNvidiaSharePrices_ShouldGiveResult() {
    runAgent(QUESTION_NVIDIA_SHARE_PRICES);
  }

  @Test
  public void whenWhoWasRulingPortugal_ShouldGiveResult() {
    runAgent(QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010);
  }

  @Test
  public void whenWeatherLondonTomorrow_ShouldGiveAnswer() {
    runAgent(QUESTION_WEATHER_LONDON_TOMORROW);
  }

  @Test
  public void whenCalculate_ShouldCalculate() {
    runAgent(QUESTION_MATH_EQUATION);
  }

  @Test
  public void whenCalculateSquareRoot_ShouldCalculate() {
    runAgent(QUESTION_SQUARE_ROOT);
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
