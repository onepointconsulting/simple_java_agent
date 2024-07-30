package com.onepointltd.model;

public record Message(String role, String content, FunctionCall functionCall) {
  public Message(String role, String content) {
    this(role, content, null);
  }

  public Message(String role, FunctionCall functionCall) {
    this(role, null, functionCall);
  }

  public Message {
    if (role == null) {
      throw new IllegalArgumentException("role cannot be null");
    }
  }
}
