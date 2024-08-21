package com.onepointltd.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepointltd.model.Message;
import com.onepointltd.model.ResponseDeserializer;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    return getStructuredResponse() ? null : answer;
  }

  @JsonProperty
  public Map<String, Object> getStructuredAnswer() throws JsonProcessingException {
    if(getStructuredResponse()) {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      return ResponseDeserializer.deserialize(answer);
    }
    return null;
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
