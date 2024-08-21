package com.onepointltd.tools;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class ToolChoiceTest {

  private final ObjectMapper serializer = new ObjectMapper();

  @Test
  void whenCreateToolChoice_ShouldCreateRightOutput() throws JsonProcessingException {

    String functionName = "test";
    var serialized = ToolChoice.serializeToolChoice(functionName);
    assertTrue(serialized.contains("tool_choice"));
    assertTrue(serialized.contains("parallel_tool_calls"));
    assertTrue(serialized.contains(functionName));
    System.out.println(serialized);
  }
}
