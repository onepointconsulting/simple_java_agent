package com.onepointltd.model;

public record Message(String role, String content) {
  public Message {
    if (role == null) {
      throw new IllegalArgumentException("role cannot be null");
    }
    if (content == null) {
      throw new IllegalArgumentException("content cannot be null");
    }
  }


}
