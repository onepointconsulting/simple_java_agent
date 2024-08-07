package com.onepointltd.agent;

import com.onepointltd.client.Client;
import com.onepointltd.model.Message;
import com.onepointltd.model.MessageExtraction;
import com.onepointltd.model.Response;
import com.onepointltd.model.Roles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Agent {
  final Client client;

  final List<Message> messages = new ArrayList<>();

  public Agent(Client client, String systemMessage) {
    this.client = client;
    if (!systemMessage.isBlank()) {
      messages.add(new Message(Roles.SYSTEM, systemMessage));
    }
  }

  public Message call(String[] userMessages, List<Map<String, Object>> toolCalls) {
    if (userMessages != null) {
      for (int i = 0; i < userMessages.length; i++) {
        String userMessage = userMessages[i];
        if (!userMessage.isBlank()) {
          boolean hasFunctionCall = toolCalls != null && !toolCalls.isEmpty();
          String id = hasFunctionCall ? (String) toolCalls.get(i).get("id") : null;
          messages.add(
              new Message(hasFunctionCall ? Roles.TOOL : Roles.USER, userMessage, null, id, null));
        }
      }
    }
    Message result = this.execute();
    messages.add(result);
    return result;
  }

  Message execute() {
    Response response = this.client.completions(messages, null);
    Optional<Message> optionalMessage = MessageExtraction.extract(response);
    return optionalMessage.orElse(new Message(Roles.ASSISTANT, "Failed to process your message"));
  }
}
