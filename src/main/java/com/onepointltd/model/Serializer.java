package com.onepointltd.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static String toJson(Object messages) throws RuntimeException {
    try {
      return MAPPER.writeValueAsString(messages);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
