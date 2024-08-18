package com.onepointltd.tools;

import com.onepointltd.model.Function;
import com.onepointltd.model.Parameters;
import com.onepointltd.model.PropertyValue;
import java.util.List;
import java.util.Map;

public interface FunctionalAbstractSearchTool extends Tool, FunctionalTool {

  public static final String SEARCH = "search";

  @Override
  public default Function function() {
    Function function = new Function(name(), description());
    Parameters parameters =
        new Parameters(
            "object",
            Map.of(SEARCH, new PropertyValue("The search expression", "string")),
            List.of(SEARCH));
    function.setParameters(parameters);
    return function;
  }
}
