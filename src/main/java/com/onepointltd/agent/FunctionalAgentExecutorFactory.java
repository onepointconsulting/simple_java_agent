package com.onepointltd.agent;

import com.onepointltd.client.Client;
import com.onepointltd.config.Config;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDateFromTodayTool;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalSerpApiTool;
import com.onepointltd.tools.FunctionalTodayTool;
import com.onepointltd.tools.FunctionalTool;
import com.onepointltd.tools.FunctionalWikipedia;

public class FunctionalAgentExecutorFactory {

  public static FunctionalAgentExecutor createDefaultAgentExecutor(Config config, Client client) {
    FunctionalTool[] toolsArray = new FunctionalTool[5];
    boolean hasSerpApi = config.getSerpApiKey() != null;
    if (!hasSerpApi) {
      toolsArray[0] = new FunctionalDuckDuckGo(config);
    } else {
      toolsArray[0] = new FunctionalSerpApiTool(config);
    }
    toolsArray[1] = new FunctionalWikipedia(config);
    toolsArray[2] = new FunctionalCalculator();
    toolsArray[3] = new FunctionalTodayTool();
    toolsArray[4] = new FunctionalDateFromTodayTool();
    return new FunctionalAgentExecutor(client, toolsArray, config.getAgentMaxIterations());
  }
}
