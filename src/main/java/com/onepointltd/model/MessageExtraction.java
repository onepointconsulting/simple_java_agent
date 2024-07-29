package com.onepointltd.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessageExtraction {

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
    if(!message.containsKey("role") || !message.containsKey("content")) {
      return Optional.empty();
    }
    return Optional.of(new Message((String) message.get("role"), (String) message.get("content")));
  }
}
