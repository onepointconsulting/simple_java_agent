package com.onepointltd.agent;

import static com.onepointltd.config.Logging.logger;

import com.onepointltd.client.Client;
import com.onepointltd.model.Message;
import com.onepointltd.model.ToolCall;
import com.onepointltd.prompts.SystemMessageGenerator;
import com.onepointltd.tools.Tool;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgentExecutor {

  public static final String PAUSE = "PAUSE";

  public static final String TOOL = "Tool";
  public static final String ANSWER = "Answer:";

  final Tool[] tools;

  final Client client;

  final int maxIterations;

  protected List<Message> messages;

  public AgentExecutor(Client client, Tool[] tools, int maxIterations) {
    this.client = client;
    this.tools = tools;
    this.maxIterations = maxIterations;
  }

  Optional<ToolCall> extractAction(String content) {
    // Define the regex pattern
    String regex = "Tool:\\s*(\\w+)(:\\s*(.+))?";

    // Compile the pattern
    Pattern pattern = Pattern.compile(regex);

    // Match the pattern against the input string
    Matcher matcher = pattern.matcher(content);

    if (matcher.find()) {
      String tool = matcher.group(1);
      String query = matcher.groupCount() > 1 ? matcher.group(3) : "";

      // Print the results
      return Optional.of(new ToolCall(tool, query));
    } else {
      return Optional.empty();
    }
  }

  Agent initAgent() {
    Agent agent = new Agent(
        client, SystemMessageGenerator.generateSystemMessage(tools, tools[0].name(), false));
    messages = agent.getMessages();
    return agent;
  }

  public String execute(String question) {
    Agent agent = initAgent();
    int iteration = 0;
    String nextPrompt = question;

    while (iteration < maxIterations) {
      Message response = agent.call(new String[] {nextPrompt}, null);
      logger.info(response.toString());
      String content = response.content();
      iteration++;
      if (content.contains(PAUSE) && content.contains(TOOL)) {
        nextPrompt = generateObservation(content);
      } else if (content.contains(ANSWER)) {
        return content;
      } else {
        nextPrompt = "";
      }
    }
    return "Failed to find an answer";
  }

  String generateObservation(String content) {
    String nextPrompt;
    Optional<ToolCall> optionalToolCall = extractAction(content);
    if (optionalToolCall.isPresent()) {
      ToolCall toolCall = optionalToolCall.get();
      Optional<Tool> toolOptional = findTool(toolCall);
      if (toolOptional.isPresent()) {
        Tool tool = toolOptional.get();
        nextPrompt = produceObservation(toolCall, tool);
        logger.info(nextPrompt);
      } else {
        nextPrompt = String.format("Observation: Cannot find tool %s", toolCall.toolName());
      }
    } else {
      nextPrompt = String.format("Failed to extract tool call %s", content);
    }
    return nextPrompt;
  }

  static String produceObservation(ToolCall toolCall, Tool tool) {
    return String.format("Observation: %s", tool.execute(toolCall.value()));
  }

  Optional<Tool> findTool(ToolCall toolCall) {
    return Arrays.stream(tools).filter(tool -> tool.name().equals(toolCall.toolName())).findFirst();
  }

  public Client getClient() {
    return client;
  }

  public String getEndpoint() {
    if(client == null) {
      return null;
    }
    return client.getEndpoint();
  }

  public List<Message> getMessages() {
    return messages;
  }
}
