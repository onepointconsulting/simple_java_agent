package com.onepointltd.aagent;

import static com.onepointltd.aagent.AgentRunnerFunction.rungAgentGroq;
import static com.onepointltd.provider.QuestionProvider.QUESTION_EARTH_JUPITER_DIAMETERS;
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
    rungAgentGroq(QUESTION_OLYMPIC);
  }

  @Test
  public void whenNvidiaSharePrices_ShouldGiveResult() {
    rungAgentGroq(QUESTION_NVIDIA_SHARE_PRICES);
  }

  @Test
  public void whenWhoWasRulingPortugal_ShouldGiveResult() {
    rungAgentGroq(QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010);
  }

  @Test
  public void whenWeatherLondonTomorrow_ShouldGiveAnswer() {
    rungAgentGroq(QUESTION_WEATHER_LONDON_TOMORROW);
  }

  @Test
  public void whenCalculate_ShouldCalculate() {
    rungAgentGroq(QUESTION_MATH_EQUATION);
  }

  @Test
  public void whenCalculateSquareRoot_ShouldCalculate() {
    rungAgentGroq(QUESTION_SQUARE_ROOT);
  }

  @Test
  public void whenRationEarthJupiter_WithMassOfEarth() {
    rungAgentGroq(QUESTION_EARTH_JUPITER_DIAMETERS);
  }


}
