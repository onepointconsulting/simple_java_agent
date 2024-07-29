package com.onepointltd.model;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.jupiter.api.Test;

class MessagesSerializerTest {

  @Test
  void toJson() throws JsonProcessingException {
    String json = MessagesSerializer.toJson(MessageListProvider.provide());
    assertNotNull(json);
    System.out.println(json);
  }
}