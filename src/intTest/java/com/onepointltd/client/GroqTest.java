package com.onepointltd.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.onepointltd.config.Config;
import com.onepointltd.model.Function;
import com.onepointltd.model.Message;
import com.onepointltd.model.MessageExtraction;
import com.onepointltd.model.ParameterType;
import com.onepointltd.model.Parameters;
import com.onepointltd.model.PropertyValue;
import com.onepointltd.model.Response;
import com.onepointltd.model.ToolField;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class GroqTest {

  private final Config config = new Config();

  private final Groq groq = new Groq(config);

  @Test
  void whenSendUserMessageNoFunctions_ShouldReceiveResponse() {
    processGroq(new Message("user", "What is the square root of 2?"), null);
  }

  private void processGroq(Message inputMessage, Function function) {
    Response response = groq.completions(List.of(inputMessage), function == null ? null : Stream.of(function).map(ToolField::new).toList());
    assertNotNull(response);
    assertEquals(200, response.statusCode());
    Optional<Message> optionalMessage = MessageExtraction.extract(response);
    assertTrue(optionalMessage.isPresent());
    Message message = optionalMessage.get();
    assertTrue(message.content() != null || message.functionCall() != null);
    if(message.content() != null)
      System.out.println(message.content());
    if (message.functionCall() != null)
      System.out.println(message.functionCall());
    assertNotNull(message.role());
  }

  @Test
  void whenSendUserMessageFunctions_ShouldReceiveResponse() {
    Function function = new Function("math_sqrt", "Takes the square root of a number");
    Parameters parameters = new Parameters("object", Map.of("number", new PropertyValue("The number to be printed", ParameterType.INTEGER.toString())), List.of("number"));
    function.setParameters(parameters);
    processGroq(new Message("user", "What is the square root of 2?"), function);
  }
}
