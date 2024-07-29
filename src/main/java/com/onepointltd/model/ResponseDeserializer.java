package com.onepointltd.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class ResponseDeserializer {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static Map<String, Object> deserialize(String response) throws JsonProcessingException {
    return MAPPER.readValue(response, new TypeReference<>() {});
  }
}
