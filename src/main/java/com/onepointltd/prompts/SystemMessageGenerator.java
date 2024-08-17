package com.onepointltd.prompts;

import com.onepointltd.tools.Tool;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SystemMessageGenerator {

  public static String generateSystemMessage(Tool[] tools, String defaultTool, boolean isFunctional) {
    try (InputStream in =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(
            isFunctional ? "system_functional.txt" : "system.txt")) {
      if (in == null) {
        throw new IOException("system.txt not found");
      }
      String systemMessage = new Scanner(in).useDelimiter("\\Z").next();
      String message = generateToolDescriptions(tools).trim();
      return systemMessage.replace("{tools}", message).replace("{default_tool}", defaultTool);
    } catch (IOException e) {
      throw new RuntimeException("Failed to generate system message", e);
    }
  }

  private static String generateToolDescriptions(Tool[] tools) {
    StringBuilder message = new StringBuilder();
    for (Tool tool : tools) {
      message.append(
          String.format("""
%s:
e.g. %s
%s

""", tool.name(), tool.example(), tool.description()));
    }
    return message.toString();
  }
}
