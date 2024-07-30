package com.onepointltd.client;

import static com.onepointltd.config.Logging.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onepointltd.config.Config;
import com.onepointltd.model.Function;
import com.onepointltd.model.Message;
import com.onepointltd.model.Serializer;
import com.onepointltd.model.Response;
import com.onepointltd.model.ResponseDeserializer;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class Groq implements Client {

  private static final String GROQ_ENDPOINT = "https://api.groq.com/openai/v1/chat/completions";

  private static final String TEMPLATE = """
{
  "model": "%s",
  "messages": %s,
  "stream": false
}
      """;

  private static final String FUNCTION_TEMPLATE = """
{
  "model": "%s",
  "messages": %s,
  "stream": false,
  "functions": %s
}
      """;

  private final Config config;

  public Groq(Config config) {
    this.config = config;
  }

  public Response completions(List<Message> messages, List<Function> functions) {
    HttpClient client = HttpClient.newHttpClient();
    try {
      String jsonBody = createBody(messages, functions);
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(GROQ_ENDPOINT))
          .timeout(Duration.ofSeconds(config.getTimeout()))
          .header("Authorization", "Bearer " + config.getApiKey())
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
          .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      String body = response.body();
      return new Response(response.statusCode(), body, ResponseDeserializer.deserialize(body));
    } catch (JsonProcessingException e) {
      logger.log(Level.SEVERE, "Failed to serialize messages", e);
      return new Response(400, e.getMessage(), Collections.emptyMap());
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to communicate with server", e);
      return new Response(400, e.getMessage(), Collections.emptyMap());
    } catch (InterruptedException e) {
      logger.log(Level.SEVERE, "Thread got interrupted", e);
      return new Response(400, e.getMessage(), Collections.emptyMap());
    }
  }

  public String createBody(List<Message> messages, List<Function> functions) {
    String messagesJson = Serializer.toJson(messages).replace("functionCall", "function_call");
    if(functions != null && !functions.isEmpty()) {
      String functionJson = Serializer.toJson(functions);
      return String.format(FUNCTION_TEMPLATE, config.getModelName(), messagesJson, functionJson);
    } else {
      return String.format(TEMPLATE, config.getModelName(), messagesJson);
    }
  }
}
