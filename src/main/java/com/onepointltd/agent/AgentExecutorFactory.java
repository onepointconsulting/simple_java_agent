package com.onepointltd.agent;

import com.onepointltd.client.Client;
import com.onepointltd.config.Config;
import com.onepointltd.tools.Calculator;
import com.onepointltd.tools.DateFromTodayTool;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.SerpAPITool;
import com.onepointltd.tools.TodayTool;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;

public class AgentExecutorFactory {

  public static AgentExecutor createDefaultAgentExecutor(Config config, Client client) {
    Tool[] toolsArray = new Tool[5];
    boolean hasSerpApi = config.getSerpApiKey() != null;
    if (!hasSerpApi) {
      toolsArray[0] = new DuckDuckGo(config);
    } else {
      toolsArray[0] = new SerpAPITool(config);
    }
    toolsArray[1] = new Wikipedia(config);
    toolsArray[2] = new Calculator();
    toolsArray[3] = new TodayTool();
    toolsArray[4] = new DateFromTodayTool();
    return new AgentExecutor(client, toolsArray, config.getAgentMaxIterations());
  }
}
