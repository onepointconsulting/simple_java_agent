package com.onepointltd.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessagesSerializer {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static String toJson(Object messages) throws RuntimeException {
    try {
      return MAPPER.writeValueAsString(messages);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
