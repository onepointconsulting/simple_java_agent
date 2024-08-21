package com.onepointltd.tools;

import static com.onepointltd.config.Logging.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.logging.Level;

public class ToolChoice {

  public static Map<String, Object> createToolChoice(String functionName) {
    return Map.of(
        "parallel_tool_calls",
        false,
        "tool_choice",
        Map.of("type", "function", "function", Map.of("name", functionName)));
  }

  public static String serializeToolChoice(String functionName) {
    ObjectMapper serializer = new ObjectMapper();
    try {
      String json = serializer.writeValueAsString(createToolChoice(functionName));
      return json.replaceFirst("\\{", "").replaceFirst("}", "");
    } catch (JsonProcessingException e) {
      logger.log(Level.SEVERE, "Failed to serialize tool choice", e);
      return "";
    }
  }
}
