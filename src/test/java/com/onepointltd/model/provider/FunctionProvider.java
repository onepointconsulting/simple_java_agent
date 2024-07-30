package com.onepointltd.model.provider;

import com.onepointltd.model.Function;
import com.onepointltd.model.Parameters;
import com.onepointltd.model.PropertyValue;
import java.util.Map;

public class FunctionProvider {

  public static Function createFunction() {
    Function function = new Function("duduckgo_search", "Search the web using DuckDuckGo.");
    Parameters parameters = new Parameters("object", Map.of("search", new PropertyValue("The search string", "string")));
    function.setParameters(parameters);
    return function;
  }
}
