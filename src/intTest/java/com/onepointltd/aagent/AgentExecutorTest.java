package com.onepointltd.aagent;

import static com.onepointltd.provider.QuestionProvider.QUESTION_MASS_EARTH_BY_2;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MATH_EQUATION;
import static com.onepointltd.provider.QuestionProvider.QUESTION_NVIDIA_SHARE_PRICES;
import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;
import static com.onepointltd.provider.QuestionProvider.QUESTION_UK_PRIME_MINISTER;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WEATHER_LONDON_TOMORROW;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010;

import com.onepointltd.agent.AgentExecutor;
import com.onepointltd.client.Groq;
import com.onepointltd.config.Config;
import com.onepointltd.tools.Calculator;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;
import org.junit.jupiter.api.Test;

public class AgentExecutorTest {

  @Test
  public void whenOlympicGames_ShouldBeParis() {
    runAgent(QUESTION_OLYMPIC);
  }

  @Test
  public void whenWhoIsUKPrimeMinister_ShouldBeKeir() {
    runAgent(QUESTION_UK_PRIME_MINISTER);
  }

  @Test
  public void whenNvidiaSharePricesToday_ShouldBeKeir() {
    runAgent(QUESTION_NVIDIA_SHARE_PRICES);
  }

  @Test
  public void whenWhoRuledPortugalin2010_ShouldGiveAnswer() {
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
  public void whenCalculate_WithMassOfEarth() {
    runAgent(QUESTION_MASS_EARTH_BY_2);
  }

  private static void runAgent(String question) {
    Config config = new Config();
    System.out.printf("Model: %s%n", config.getModelName());
    AgentExecutor agentExecutor = new AgentExecutor(new Groq(config),
        new Tool[]{new DuckDuckGo(config), new Wikipedia(config), new Calculator()}, 5);
    String answer = agentExecutor.execute(question);
    System.out.println("*****************************");
    System.out.println(answer);
    System.out.println("*****************************");
  }
}
