package com.onepointltd.agent;

import static com.onepointltd.config.Logging.logger;

import com.onepointltd.client.Client;
import com.onepointltd.model.Function;
import com.onepointltd.model.Message;
import com.onepointltd.model.ResponseDeserializer;
import com.onepointltd.model.ToolCall;
import com.onepointltd.model.ToolField;
import com.onepointltd.prompts.SystemMessageGenerator;
import com.onepointltd.tools.Calculator;
import com.onepointltd.tools.DateFromTodayTool;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDateFromTodayTool;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalSerpApiTool;
import com.onepointltd.tools.FunctionalTool;
import com.onepointltd.tools.FunctionalWikipedia;
import com.onepointltd.tools.SerpAPITool;
import com.onepointltd.tools.TodayTool;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

public class FunctionalAgentExecutor extends AgentExecutor {

  public FunctionalAgentExecutor(Client client, FunctionalTool[] tools, int maxIterations) {
    super(client, tools, maxIterations);
  }

  @Override
  Agent initAgent() {
    List<Function> functions =
        Arrays.stream((FunctionalTool[]) super.tools).map(FunctionalTool::function).toList();
    List<ToolField> tools = functions.stream().map(ToolField::new).toList();
    FunctionalAgent functionalAgent = new FunctionalAgent(
        super.client,
        SystemMessageGenerator.generateSystemMessage(super.tools, super.tools[0].name(), true),
        tools);
    messages = functionalAgent.getMessages();
    return functionalAgent;
  }

  public String execute(String question) {
    Agent agent = initAgent();
    int iteration = 0;
    String[] nextPrompt = {question};
    List<Map<String, Object>> toolCalls = null;
    while (iteration < maxIterations) {
      Message response = agent.call(nextPrompt, toolCalls);
      logger.info(response.toString());
      toolCalls = response.toolCalls();
      String content = response.content();
      iteration++;
      if (toolCalls != null) {
        nextPrompt = extractToolCalls(toolCalls);
      } else if (content.contains(ANSWER)) {
        return content;
      } else {
        nextPrompt = new String[] {""};
      }
    }
    return "Failed to find an answer";
  }

  @SuppressWarnings("unchecked")
  private String[] extractToolCalls(List<Map<String, Object>> toolCalls) {
    String[] returnToolCalls = new String[toolCalls.size()];
    for (int i = 0; i < toolCalls.size(); i++) {
      Map<String, Object> tool = toolCalls.get(i);
      Map<String, Object> function = (Map<String, Object>) tool.get("function");
      String name = (String) function.get("name");
      String arguments = (String) function.get("arguments");
      try {
        Map<String, Object> args = ResponseDeserializer.deserialize(arguments);
        ToolCall toolCall = null;
        switch (name) {
          case DuckDuckGo.NAME -> {
            String search = args.get(FunctionalDuckDuckGo.SEARCH).toString();
            toolCall = new ToolCall(DuckDuckGo.NAME, search);
          }
          case SerpAPITool.NAME -> {
            String search = args.get(FunctionalSerpApiTool.SEARCH).toString();
            toolCall = new ToolCall(SerpAPITool.NAME, search);
          }
          case Wikipedia.NAME -> {
            String search = args.get(FunctionalWikipedia.SEARCH).toString();
            toolCall = new ToolCall(Wikipedia.NAME, search);
          }
          case Calculator.NAME -> {
            String expression = args.get(FunctionalCalculator.EXPRESSION).toString();
            toolCall = new ToolCall(Calculator.NAME, expression);
          }
          case TodayTool.NAME -> {
            toolCall = new ToolCall(TodayTool.NAME, null);
          }
          case DateFromTodayTool.NAME -> {
            String dateDifferenceDays =
                args.get(FunctionalDateFromTodayTool.DATE_DIFFERENCE_DAYS).toString();
            toolCall = new ToolCall(DateFromTodayTool.NAME, dateDifferenceDays);
          }
        }
        if (toolCall != null) {
          Optional<Tool> toolOptional = findTool(toolCall);
          if (toolOptional.isPresent()) {
            returnToolCalls[i] = produceObservation(toolCall, toolOptional.get());
          }
        } else {
          returnToolCalls[i] = "Observation: Tool call not found";
        }
      } catch (Exception e) {
        logger.log(Level.SEVERE, "Failed to extract tool call", e);
        returnToolCalls[i] = "Observation: Failed to extract tool call";
      }
    }
    return returnToolCalls;
  }
}
