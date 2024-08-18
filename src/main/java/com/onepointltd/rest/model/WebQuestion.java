package com.onepointltd.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.onepointltd.model.AgentType;

public class WebQuestion {

  private String question;

  private String agentType;

  public WebQuestion() {
  }

  public WebQuestion(String question, String agentType) {
    this.question = question;
    this.agentType = agentType == null ? AgentType.PLAIN : AgentType.AGENT_TYPES.contains(agentType)
        ? agentType : AgentType.PLAIN;
  }

  @JsonProperty
  public String getQuestion() {
    return question;
  }

  @JsonProperty
  public String getAgentType() {
    return agentType;
  }
}
