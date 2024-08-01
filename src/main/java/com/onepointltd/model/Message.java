package com.onepointltd.model;

import java.util.List;
import java.util.Map;

public record Message(String role, String content, FunctionCall functionCall, String tool_call_id, List<Map<String, Object>> toolCalls) {
  public Message(String role, String content) {
    this(role, content, null, null, null);
  }

  public Message(String role, FunctionCall functionCall) {
    this(role, null, functionCall, null, null);
  }

  public Message(String role, List<Map<String, Object>> toolCalls) {
    this(role, null, null, null, toolCalls);
  }

  public Message {
    if (role == null) {
      throw new IllegalArgumentException("role cannot be null");
    }
  }
}
