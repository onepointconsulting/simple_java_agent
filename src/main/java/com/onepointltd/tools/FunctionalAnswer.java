package com.onepointltd.tools;

import com.onepointltd.model.Function;
import com.onepointltd.model.Items;
import com.onepointltd.model.Parameters;
import com.onepointltd.model.PropertyValue;
import java.util.List;
import java.util.Map;

/**
 * This tool is only used to format the answers in a structured way. It should never be executed.
 */
public class FunctionalAnswer extends AbstractTool implements FunctionalTool {

  private static final String ANSWER = "answer";

  private static final String REASONING = "reasoning";

  private static final String KEYWORDS = "keywords";

  @Override
  public String execute(String input) {
    return input;
  }

  @Override
  public String name() {
    return "Answer";
  }

  @Override
  public String example() {
    return "Answer(\"The weather will be cloudy\", "
        + "\"Reasoning: The observations from the weather report indicate that the sky is covered with clouds. The probability of rain is high. The temperature is low. The wind is strong. The humidity is high.\"";
  }

  @Override
  public String description() {
    return "Provide a structured answer with separate reasoning.";
  }

  @Override
  public Function function() {
    Function function = new Function(name(), description());
    Parameters parameters =
        new Parameters(
            "object",
            Map.of(
                ANSWER,
                new PropertyValue("The actual answer", "string"),
                REASONING,
                new PropertyValue(
                    "The reasoning behind the answer related to the observations", "string"),
                KEYWORDS,
                new PropertyValue(
                    "The keywords related to the answer", "array", new Items("string"))),
            List.of(ANSWER, KEYWORDS));
    function.setParameters(parameters);
    return function;
  }
}
