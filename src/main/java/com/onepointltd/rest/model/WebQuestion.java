package com.onepointltd.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebQuestion {

  private final String question;

  public WebQuestion(String question) {
    this.question = question;
  }

  @JsonProperty
  public String getQuestion() {
    return question;
  }
}
