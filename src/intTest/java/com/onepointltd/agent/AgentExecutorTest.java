package com.onepointltd.agent;

import static com.onepointltd.agent.AgentRunnerFunction.rungAgentGroqSimple;
import static com.onepointltd.provider.QuestionProvider.QUESTION_EARTH_JUPITER_DIAMETERS;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MASS_EARTH_BY_2;
import static com.onepointltd.provider.QuestionProvider.QUESTION_MATH_EQUATION;
import static com.onepointltd.provider.QuestionProvider.QUESTION_NVIDIA_SHARE_PRICES;
import static com.onepointltd.provider.QuestionProvider.QUESTION_OLYMPIC;
import static com.onepointltd.provider.QuestionProvider.QUESTION_TODAY;
import static com.onepointltd.provider.QuestionProvider.QUESTION_TOMORROW;
import static com.onepointltd.provider.QuestionProvider.QUESTION_UK_PRIME_MINISTER;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WEATHER_LONDON_TOMORROW;
import static com.onepointltd.provider.QuestionProvider.QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010;

import org.junit.jupiter.api.Test;

public class AgentExecutorTest {

  @Test
  public void whenOlympicGames_ShouldBeParis() {
    rungAgentGroqSimple(QUESTION_OLYMPIC);
  }

  @Test
  public void whenWhoIsUKPrimeMinister_ShouldBeKeir() {
    rungAgentGroqSimple(QUESTION_UK_PRIME_MINISTER);
  }

  @Test
  public void whenNvidiaSharePricesToday_ShouldBeKeir() {
    rungAgentGroqSimple(QUESTION_NVIDIA_SHARE_PRICES);
  }

  @Test
  public void whenWhoRuledPortugalin2010_ShouldGiveAnswer() {
    rungAgentGroqSimple(QUESTION_WHO_WAS_RULING_PORTUGAL_IN_2010);
  }

  @Test
  public void whenWeatherLondonTomorrow_ShouldGiveAnswer() {
    rungAgentGroqSimple(QUESTION_WEATHER_LONDON_TOMORROW);
  }

  @Test
  public void whenCalculate_ShouldCalculate() {
    rungAgentGroqSimple(QUESTION_MATH_EQUATION);
  }

  @Test
  public void whenCalculate_WithMassOfEarth() {
    rungAgentGroqSimple(QUESTION_MASS_EARTH_BY_2);
  }

  @Test
  public void whenRationEarthJupiter_WithMassOfEarth() {
    rungAgentGroqSimple(QUESTION_EARTH_JUPITER_DIAMETERS);
  }

  @Test
  public void whenTodayDate_ShouldRetrieveDate() {
    rungAgentGroqSimple(QUESTION_TODAY);
  }

  @Test
  public void whenTomorrowsDate_ShouldRetrieveDate() {
    rungAgentGroqSimple(QUESTION_TOMORROW);
  }
}
