package com.onepointltd.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onepointltd.model.AgentType;

public class WebQuestion {

  private String question;

  private String agentType;

  private boolean includeMessages;

  public WebQuestion() {}

  public WebQuestion(String question, String agentType, boolean includeMessages) {
    this.question = question;
    this.agentType =
        agentType == null
            ? AgentType.PLAIN
            : AgentType.AGENT_TYPES.contains(agentType) ? agentType : AgentType.PLAIN;
    this.includeMessages = includeMessages;
  }

  @JsonProperty
  public String getQuestion() {
    return question;
  }

  @JsonProperty
  public String getAgentType() {
    return agentType;
  }

  @JsonProperty
  public boolean getIncludeMessages() {
    return includeMessages;
  }
}
