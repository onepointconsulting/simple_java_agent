package com.onepointltd.agent;

import com.onepointltd.client.Client;
import com.onepointltd.model.Response;
import com.onepointltd.model.ToolField;
import java.util.List;

public class FunctionalAgent extends Agent {

  private List<ToolField> tools;

  public FunctionalAgent(Client client, String systemMessage, List<ToolField> tools) {
    super(client, systemMessage);
    this.tools = tools;
  }

  protected Response callClient() {
    return super.client.completions(messages, tools);
  }

  public void setTools(List<ToolField> tools) {
    this.tools = tools;
  }
}
