package com.onepointltd.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class MessageExtractionTest {

  @SuppressWarnings("unchecked")
  @Test
  void whenExtractAnswer_ShouldContainReasoning() throws IOException {
    try (InputStream in =
        Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("json/functionalanswer.json")) {
      assertNotNull(in);
      var scanner = new Scanner(in).useDelimiter("\\Z");
      var json = scanner.next();
      var response = ResponseDeserializer.deserialize(json);
      var answer = MessageExtraction.extractAnswer(List.of(response));
      assertTrue(answer.contains("answer"));
    }
  }
}
