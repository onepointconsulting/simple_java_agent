package com.onepointltd.aagent;

import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;

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
    String question = "Who is the uK prime minister?";
    runAgent(question);
  }

  @Test
  public void whenNvidiaSharePricesToday_ShouldBeKeir() {
    String question = "Can you tell me Nvidia Share prices as of today?";
    runAgent(question);
  }

  @Test
  public void whenWhoRuledPortugalin2010_ShouldGiveAnswer() {
    String question = "Who was ruling Portugal in 2010?";
    runAgent(question);
  }

  @Test
  public void whenWeatherLondonTomorrow_ShouldGiveAnswer() {
    String question = "How will the weather in London be tomorrow? Please mention the temperature in Celsius.";
    runAgent(question);
  }

  @Test
  public void whenCalculate_ShouldCalculate() {
    String question = "What is the result of 2 * 3 + 4 / 2 * 10?";
    runAgent(question);
  }

  @Test
  public void whenCalculate_WithMassOfEarth() {
    String question = "What is the mass of the earth multiplied by 3?";
    runAgent(question);
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
