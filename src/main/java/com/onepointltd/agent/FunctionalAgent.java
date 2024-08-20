package com.onepointltd.agent;

import com.onepointltd.client.Client;
import com.onepointltd.model.Message;
import com.onepointltd.model.MessageExtraction;
import com.onepointltd.model.Response;
import com.onepointltd.model.Roles;
import com.onepointltd.model.ToolField;
import java.util.List;
import java.util.Optional;

public class FunctionalAgent extends Agent {

  private final List<ToolField> tools;

  public FunctionalAgent(Client client, String systemMessage, List<ToolField> tools) {
    super(client, systemMessage);
    this.tools = tools;
  }

  protected Response callClient() {
    return super.client.completions(messages, tools);
  }
}
