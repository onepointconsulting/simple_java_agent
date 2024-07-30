package com.onepointltd.model;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

class SerializerTest {

  @Test
  void toJson() throws JsonProcessingException {
    String json = Serializer.toJson(MessageListProvider.provide());
    assertNotNull(json);
    System.out.println(json);
  }
}