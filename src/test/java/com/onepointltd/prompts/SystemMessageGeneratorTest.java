package com.onepointltd.prompts;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.onepointltd.config.Config;
import com.onepointltd.tools.DuckDuckGo;
import com.onepointltd.tools.Tool;
import com.onepointltd.tools.Wikipedia;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class SystemMessageGeneratorTest {

  @Test
  void generateSystemMessage() throws IOException {
    DuckDuckGo duckDuckGo = new DuckDuckGo(new Config());
    Wikipedia wikipedia = new Wikipedia(new Config());
    String systemMessage =
        SystemMessageGenerator.generateSystemMessage(
            new Tool[] {duckDuckGo, wikipedia}, duckDuckGo.name(), false);
    assertNotNull(systemMessage);
    System.out.println(systemMessage);
  }
}
