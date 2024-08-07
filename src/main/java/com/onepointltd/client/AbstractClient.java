package com.onepointltd.client;

import static com.onepointltd.config.Logging.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.onepointltd.config.Config;
import com.onepointltd.model.Message;
import com.onepointltd.model.Response;
import com.onepointltd.model.ResponseDeserializer;
import com.onepointltd.model.Serializer;
import com.onepointltd.model.ToolField;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public abstract class AbstractClient implements Client {

  final Config config;

  final String endpoint;

  static final String TEMPLATE =
      """
{
  "model": "%s",
  "messages": %s,
  "stream": false
}
      """;

  static final String FUNCTION_TEMPLATE =
      """
{
  "model": "%s",
  "messages": %s,
  "stream": false,
  "tools": %s
}
      """;

  protected AbstractClient(Config config, String endpoint) {
    this.config = config;
    this.endpoint = endpoint;
  }

  public Response completions(List<Message> messages, List<ToolField> tools) {
    HttpClient client = HttpClient.newHttpClient();
    try {
      String jsonBody = createBody(messages, tools);
      logger.info("Sending request to " + this.endpoint + " with body: " + jsonBody);
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(this.endpoint))
              .timeout(Duration.ofSeconds(config.getTimeout()))
              .header("Authorization", "Bearer " + config.getApiKey())
              .header("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
              .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      String body = response.body();
      logger.info("Response body " + body);
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

  public String createBody(List<Message> messages, List<ToolField> tools) {
    String messagesJson = Serializer.toJson(messages).replaceAll("toolCalls", "tool_calls");
    if (tools != null && !tools.isEmpty()) {
      String toolsJson = Serializer.toJson(tools);
      return String.format(FUNCTION_TEMPLATE, config.getModelName(), messagesJson, toolsJson);
    } else {
      return String.format(TEMPLATE, config.getModelName(), messagesJson);
    }
  }
}
