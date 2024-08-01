package com.onepointltd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import org.junit.jupiter.api.Test;

class ResponseTest {

  private final Response response = new Response(200, "body", Collections.emptyMap());

  @Test
  void statusCode() {
    assertEquals(200, response.statusCode());
  }

  @Test
  void body() {
    assertEquals("body", response.body());
  }
}