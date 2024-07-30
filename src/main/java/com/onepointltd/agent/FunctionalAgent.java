package com.onepointltd.agent;

import com.onepointltd.client.Client;
import com.onepointltd.model.Function;
import com.onepointltd.model.Message;
import com.onepointltd.model.MessageExtraction;
import com.onepointltd.model.Response;
import com.onepointltd.model.Roles;
import java.util.List;
import java.util.Optional;

public class FunctionalAgent extends Agent {

  private final List<Function> functions;

  public FunctionalAgent(Client client, String systemMessage, List<Function> functions) {
    super(client, systemMessage);
    this.functions = functions;
  }

  Message execute() {
    Response response = super.client.completions(messages, functions);
    Optional<Message> optionalMessage = MessageExtraction.extract(response);
    return optionalMessage.orElse(new Message(Roles.ASSISTANT, "Failed to process your message"));
  }
}
