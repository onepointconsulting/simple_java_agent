package com.onepointltd.agent;

import static com.onepointltd.agent.AgentRunnerFunction.rungAgentGroqFunctional;
import static com.onepointltd.provider.QuestionProvider.QUESTION_EARTH_JUPITER_DIAMETERS;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MATH_EQUATION;
import static com.onepointltd.provider.QuestionProvider.QUESTION_NVIDIA_SHARE_PRICES;
import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;
import static com.onepointltd.provider.QuestionProvider.QUESTION_SQUARE_ROOT;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WEATHER_LONDON_TOMORROW;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010;

import org.junit.jupiter.api.Test;

public class FunctionAgentExecutorTest {

  @Test
  public void whenOlympicGames_ShouldBeParis() {
    rungAgentGroqFunctional(QUESTION_OLYMPIC);
  }

  @Test
  public void whenNvidiaSharePrices_ShouldGiveResult() {
    rungAgentGroqFunctional(QUESTION_NVIDIA_SHARE_PRICES);
  }

  @Test
  public void whenWhoWasRulingPortugal_ShouldGiveResult() {
    rungAgentGroqFunctional(QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010);
  }

  @Test
  public void whenWeatherLondonTomorrow_ShouldGiveAnswer() {
    rungAgentGroqFunctional(QUESTION_WEATHER_LONDON_TOMORROW);
  }

  @Test
  public void whenCalculate_ShouldCalculate() {
    rungAgentGroqFunctional(QUESTION_MATH_EQUATION);
  }

  @Test
  public void whenCalculateSquareRoot_ShouldCalculate() {
    rungAgentGroqFunctional(QUESTION_SQUARE_ROOT);
  }

  @Test
  public void whenRationEarthJupiter_WithMassOfEarth() {
    rungAgentGroqFunctional(QUESTION_EARTH_JUPITER_DIAMETERS);
  }
}
