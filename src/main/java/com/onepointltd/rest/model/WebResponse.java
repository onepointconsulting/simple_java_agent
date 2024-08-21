package com.onepointltd.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onepointltd.model.Message;
import java.util.List;

public class WebResponse extends WebQuestion {

  private final String answer;

  private final String endpoint;

  private List<Message> messages;

  public WebResponse(
      String question,
      String answer,
      String agentType,
      String endpoint,
      boolean structuredResponse) {
    super(question, agentType, false, structuredResponse);
    this.answer = answer;
    this.endpoint = endpoint;
  }

  @JsonProperty
  public String getAnswer() {
    return answer;
  }

  @JsonProperty
  public String getEndpoint() {
    return endpoint;
  }

  @JsonProperty
  public List<Message> getMessages() {
    return messages;
  }

  public WebResponse setMessages(List<Message> messages) {
    this.messages = messages;
    return this;
  }

  @Override
  public String toString() {
    return "WebResponse{" + "message='" + answer + '\'' + '}';
  }
}
