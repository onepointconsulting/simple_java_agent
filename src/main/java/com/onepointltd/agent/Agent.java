package com.onepointltd.agent;

import com.onepointltd.client.Client;
import com.onepointltd.model.Message;
import com.onepointltd.model.MessageExtraction;
import com.onepointltd.model.Response;
import com.onepointltd.model.Roles;
import java.util.ArrayList;
import java.util.List;
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

  public Message call(String userMessage) {
    if(userMessage != null && !userMessage.isBlank()) {
      messages.add(new Message(Roles.USER, userMessage));
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
