package com.onepointltd.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MessageTest {

  private final Message message = new Message("role", "content");

  @Test
  void role() {
    assertNotNull(message.role());
  }

  @Test
  void content() {
    assertNotNull(message.content());
  }
}