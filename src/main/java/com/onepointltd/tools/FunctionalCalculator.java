package com.onepointltd.tools;

import com.onepointltd.model.Function;
import com.onepointltd.model.Parameters;
import com.onepointltd.model.PropertyValue;
import java.util.List;
import java.util.Map;

public class FunctionalCalculator extends Calculator implements FunctionalTool {

  public static final String EXPRESSION = "expression";

  @Override
  public Function function() {
    Function function = new Function(super.name(), super.description());
    Parameters parameters =
        new Parameters(
            "object",
            Map.of(
                EXPRESSION, new PropertyValue("The expression used in the calculation", "string")),
            List.of("expression"));
    function.setParameters(parameters);
    return function;
  }
}
