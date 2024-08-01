package com.onepointltd.aagent;

import static com.onepointltd.aagent.AgentRunnerFunction.rungAgentOpenAI;
import static com.onepointltd.provider.QuestionProvider.QUESTION_EARTH_JUPITER_DIAMETERS;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MASS_EARTH_BY_2;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MATH_EQUATION;
import static com.onepointltd.provider.QuestionProvider.QUESTION_NVIDIA_SHARE_PRICES;
import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;
import static com.onepointltd.provider.QuestionProvider.QUESTION_UK_PRIME_MINISTER;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WEATHER_LONDON_TOMORROW;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010;

import org.junit.jupiter.api.Test;

public class AgentOpenAIExecutorTest {

  @Test
  public void whenOlympicGames_ShouldBeParis() {
    rungAgentOpenAI(QUESTION_OLYMPIC);
  }

  @Test
  public void whenWhoIsUKPrimeMinister_ShouldBeKeir() {
    rungAgentOpenAI(QUESTION_UK_PRIME_MINISTER);
  }

  @Test
  public void whenNvidiaSharePricesToday_ShouldBeKeir() {
    rungAgentOpenAI(QUESTION_NVIDIA_SHARE_PRICES);
  }

  @Test
  public void whenWhoRuledPortugalin2010_ShouldGiveAnswer() {
    rungAgentOpenAI(QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010);
  }

  @Test
  public void whenWeatherLondonTomorrow_ShouldGiveAnswer() {
    rungAgentOpenAI(QUESTION_WEATHER_LONDON_TOMORROW);
  }

  @Test
  public void whenCalculate_ShouldCalculate() {
    rungAgentOpenAI(QUESTION_MATH_EQUATION);
  }

  @Test
  public void whenCalculate_WithMassOfEarth() {
    rungAgentOpenAI(QUESTION_MASS_EARTH_BY_2);
  }

  @Test
  public void whenRationEarthJupiter_WithMassOfEarth() {
    rungAgentOpenAI(QUESTION_EARTH_JUPITER_DIAMETERS);
  }


}
