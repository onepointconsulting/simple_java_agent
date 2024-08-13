package com.onepointltd.tools;

import com.onepointltd.model.Function;
import com.onepointltd.model.Parameters;
import com.onepointltd.model.PropertyValue;
import java.util.List;
import java.util.Map;

public class FunctionalDateFromTodayTool extends DateFromTodayTool implements FunctionalTool {

  public static final String DATE_DIFFERENCE_DAYS = "date_difference_days";

  @Override
  public Function function() {
    Function function = new Function(super.name(), super.description());
    Parameters parameters =
        new Parameters(
            "object",
            Map.of(
                DATE_DIFFERENCE_DAYS,
                new PropertyValue(
                    "The difference in days from today. May be a positive or negative integer",
                    "string")),
            List.of(DATE_DIFFERENCE_DAYS));
    function.setParameters(parameters);
    return function;
  }
}
