package com.onepointltd.aagent;

import static com.onepointltd.aagent.AgentRunnerFunction.rungAgentGroq;
import static com.onepointltd.provider.QuestionProvider.QUESTION_EARTH_JUPITER_DIAMETERS;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MASS_EARTH_BY_2;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MATH_EQUATION;
import static com.onepointltd.provider.QuestionProvider.QUESTION_NVIDIA_SHARE_PRICES;
import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;
import static com.onepointltd.provider.QuestionProvider.QUESTION_UK_PRIME_MINISTER;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WEATHER_LONDON_TOMORROW;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010;

import org.junit.jupiter.api.Test;

public class AgentExecutorTest {

  @Test
  public void whenOlympicGames_ShouldBeParis() {
    rungAgentGroq(QUESTION_OLYMPIC);
  }

  @Test
  public void whenWhoIsUKPrimeMinister_ShouldBeKeir() {
    rungAgentGroq(QUESTION_UK_PRIME_MINISTER);
  }

  @Test
  public void whenNvidiaSharePricesToday_ShouldBeKeir() {
    rungAgentGroq(QUESTION_NVIDIA_SHARE_PRICES);
  }

  @Test
  public void whenWhoRuledPortugalin2010_ShouldGiveAnswer() {
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
  public void whenCalculate_WithMassOfEarth() {
    rungAgentGroq(QUESTION_MASS_EARTH_BY_2);
  }

  @Test
  public void whenRationEarthJupiter_WithMassOfEarth() {
    rungAgentGroq(QUESTION_EARTH_JUPITER_DIAMETERS);
  }


}
