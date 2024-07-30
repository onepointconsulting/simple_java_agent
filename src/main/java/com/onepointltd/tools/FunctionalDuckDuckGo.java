package com.onepointltd.tools;

import com.onepointltd.config.Config;
import com.onepointltd.model.Function;
import com.onepointltd.model.Parameters;
import com.onepointltd.model.PropertyValue;
import java.util.List;
import java.util.Map;

public class FunctionalDuckDuckGo extends DuckDuckGo implements FunctionalTool {

  public static final String SEARCH = "search";

  public FunctionalDuckDuckGo(Config config) {
    super(config);
  }

  @Override
  public Function function() {
    Function function = new Function(super.name(), super.description());
    Parameters parameters = new Parameters("object", Map.of(SEARCH, new PropertyValue("The search expression", "string")), List.of("search"));
    function.setParameters(parameters);
    return function;
  }
}
