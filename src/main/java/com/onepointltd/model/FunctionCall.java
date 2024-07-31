package com.onepointltd.model;

public record FunctionCall(String name, String arguments, String id) {
  public FunctionCall {
    if (name == null) {
      throw new IllegalArgumentException("name cannot be null");
    }
    if (arguments == null) {
      throw new IllegalArgumentException("arguments cannot be null");
    }
  }

}
