package com.onepointltd.agent;

import static com.onepointltd.config.Logging.logger;

import com.onepointltd.client.Client;
import com.onepointltd.model.Function;
import com.onepointltd.model.FunctionCall;
import com.onepointltd.model.Message;
import com.onepointltd.model.ResponseDeserializer;
import com.onepointltd.model.ToolCall;
import com.onepointltd.prompts.SystemMessageGenerator;
import com.onepointltd.tools.Calculator;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.FunctionalCalculator;
import com.onepointltd.tools.FunctionalDuckDuckGo;
import com.onepointltd.tools.FunctionalTool;
import com.onepointltd.tools.FunctionalWikipedia;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class FunctionalAgentExecutor extends AgentExecutor {

  public FunctionalAgentExecutor(Client client, FunctionalTool[] tools, int maxIterations) {
    super(client, tools, maxIterations);
  }

  @Override
  Agent initAgent() {
    List<Function> functions = Arrays.stream((FunctionalTool[]) super.tools).map(FunctionalTool::function).collect(Collectors.toList());
    return new FunctionalAgent(super.client, SystemMessageGenerator.generateSystemMessage(super.tools, super.tools[0].name()), functions);
  }

  public String execute(String question) {
    Agent agent = initAgent();
    int iteration = 0;
    String nextPrompt = question;
    while (iteration < maxIterations) {
      Message response = agent.call(nextPrompt);
      System.out.println(response);
      FunctionCall functionCall = response.functionCall();
      String content = response.content();
      iteration++;
      if (functionCall != null) {
        nextPrompt = extractToolCall(functionCall);
      }
      else if (content.contains(ANSWER)) {
        return content;
      } else {
        nextPrompt = "";
      }
    }
    return "Failed to find an answer";
  }

  private String extractToolCall(FunctionCall functionCall) {
    String arguments = functionCall.arguments();
    try {
      Map<String, Object> args = ResponseDeserializer.deserialize(arguments);
      ToolCall toolCall = null;
      switch (functionCall.name()) {
        case DuckDuckGo.NAME -> {
          String search = args.get(FunctionalDuckDuckGo.SEARCH).toString();
          toolCall = new ToolCall(DuckDuckGo.NAME, search);
        }
        case Wikipedia.NAME -> {
          String search = args.get(FunctionalWikipedia.SEARCH).toString();
          toolCall = new ToolCall(Wikipedia.NAME, search);
        }
        case Calculator.NAME -> {
          String expression = args.get(FunctionalCalculator.EXPRESSION).toString();
          toolCall = new ToolCall(Calculator.NAME, expression);
        }
      }
      if(toolCall != null) {
        Optional<Tool> toolOptional = findTool(toolCall);
        if (toolOptional.isPresent()) {
          return produceObservation(toolCall, toolOptional.get());
        }
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to extract tool call", e);
    }
    return "Observation: Cannot find tool to call";
  }

}
