package com.onepointltd.model;

import static com.onepointltd.config.Logging.logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

public class MessageExtraction {

  private static final List<String> EXPECTED_KEYS = List.of("choices", "message", "function_call");
  public static final String TOOL_CALLS = "tool_calls";

  @SuppressWarnings("unchecked")
  public static Optional<Message> extract(Response response) {
    if (response.statusCode() != 200) {
      return Optional.empty();
    }
    Map<String, Object> bodyObj = response.parsedBody();
    if (!bodyObj.containsKey("choices")) {
      return Optional.empty();
    }
    Object choicesObj = bodyObj.get("choices");
    if (!(choicesObj instanceof List)) {
      return Optional.empty();
    }
    List<Object> choices = (List<Object>) choicesObj;
    if (choices.isEmpty()) {
      return Optional.empty();
    }
    Object choiceObj = choices.get(0);
    if (!(choiceObj instanceof Map)) {
      return Optional.empty();
    }
    Map<String, Object> choice = (Map<String, Object>) choiceObj;
    if (!choice.containsKey("message")) {
      return Optional.empty();
    }
    Map<String, Object> message = (Map<String, Object>) choice.get("message");
    if (EXPECTED_KEYS.stream().anyMatch(key -> !message.containsKey(key))) {
      if (message.containsKey("content") && message.get("content") != null) {
        return Optional.of(
            new Message((String) message.get("role"), (String) message.get("content")));
      } else if (message.containsKey(TOOL_CALLS)) {
        // Should be function call
        List<Map<String, Object>> toolCallsList =
            (List<Map<String, Object>>) message.get(TOOL_CALLS);
        if (toolCallsList.isEmpty()) {
          return Optional.empty();
        }
        return Optional.of(new Message((String) message.get("role"), toolCallsList));
      }
    }
    return Optional.empty();
  }

  @SuppressWarnings("unchecked")
  public static String extractAnswer(List<Map<String, Object>> jsonNode) {
    if (jsonNode == null || jsonNode.size() == 0) {
      return "Failed to extract answer json";
    }
    try {
      Map<String, Object> stringObjectMap = jsonNode.get(0);
      Map<String, Object> function = (Map<String, Object>) stringObjectMap.get("function");
      return function.get("arguments").toString();
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Failed to parse answer json", e);
      return "Failed to parse answer json";
    }
  }
}
