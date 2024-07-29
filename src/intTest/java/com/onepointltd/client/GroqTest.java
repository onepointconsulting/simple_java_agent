package com.onepointltd.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.onepointltd.config.Config;
import com.onepointltd.model.Message;
import com.onepointltd.model.MessageExtraction;
import com.onepointltd.model.Response;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class GroqTest {

  private final Config config = new Config();

  private final Groq groq = new Groq(config);

  @Test
  void whenSendUserMessage_ShouldReceiveResponse() {
    Response response = groq.completions(List.of(new Message("user", "What is the square root of 2?")));
    assertNotNull(response);
    assertEquals(200, response.statusCode());
    Optional<Message> optionalMessage = MessageExtraction.extract(response);
    assertTrue(optionalMessage.isPresent());
    Message message = optionalMessage.get();
    assertNotNull(message.content());
    assertNotNull(message.role());
  }
}
