package com.onepointltd.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebResponse extends WebQuestion {

  private final String answer;

  public WebResponse(String question, String answer) {
    super(question);
    this.answer = answer;
  }

  @JsonProperty
  public String getAnswer() {
    return answer;
  }

  @Override
  public String toString() {
    return "WebResponse{" + "message='" + answer + '\'' + '}';
  }
}
