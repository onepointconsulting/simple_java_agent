package com.onepointltd.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessageExtraction {

  private static final List<String> EXPECTED_KEYS = List.of("choices", "message", "function_call");

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
    if(!(choicesObj instanceof List)) {
      return Optional.empty();
    }
    List<Object> choices = (List<Object>) choicesObj;
    if(choices.isEmpty()) {
      return Optional.empty();
    }
    Object choiceObj = choices.get(0);
    if(!(choiceObj instanceof Map)) {
      return Optional.empty();
    }
    Map<String, Object> choice = (Map<String, Object>) choiceObj;
    if(!choice.containsKey("message")) {
      return Optional.empty();
    }
    Map<String, Object> message = (Map<String, Object>) choice.get("message");
    if(EXPECTED_KEYS.stream().anyMatch(key -> !message.containsKey(key))) {
      if(message.containsKey("content")) {
        return Optional.of(new Message((String) message.get("role"), (String) message.get("content")));
      }
      else if(message.containsKey("function_call")) {
        // Should be function call
        Map<String, Object> functionCallMap = (Map<String, Object>) message.get("function_call");
        String name = (String) functionCallMap.get("name");
        String arguments = (String) functionCallMap.get("arguments");
        return Optional.of(new Message((String) message.get("role"), new FunctionCall(name, arguments)));
      }
    }
    return Optional.empty();
  }
}
