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
  private final Client client;

  private final List<Message> messages = new ArrayList<>();

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

  private Message execute() {
    Response response = this.client.completions(messages);
    Optional<Message> optionalMessage = MessageExtraction.extract(response);
    return optionalMessage.orElse(new Message(Roles.ASSISTANT, "Failed to process your message"));
  }


}
