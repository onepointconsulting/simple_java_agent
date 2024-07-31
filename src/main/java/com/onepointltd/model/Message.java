package com.onepointltd.model;

public record Message(String role, String content, FunctionCall functionCall, String tool_call_id) {
  public Message(String role, String content) {
    this(role, content, null, null);
  }

  public Message(String role, FunctionCall functionCall) {
    this(role, null, functionCall, null);
  }

  public Message {
    if (role == null) {
      throw new IllegalArgumentException("role cannot be null");
    }
  }
}
