package com.onepointltd.tools;

import static com.onepointltd.config.Logging.logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepointltd.config.Config;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Wikipedia extends AbstractTool {

  public static final String NAME = "wikipedia";

  public static final String ENDPOINT = "https://en.wikipedia.org/w/api.php?";

  private final Config config;

  public Wikipedia(Config config) {
    this.config = config;
  }

  @Override
  public String execute(String input) {
    // Create HttpClient instance
    HttpClient client = HttpClient.newHttpClient();

    // Prepare query parameters
    Map<String, String> params =
        Map.of(
            "action", "query",
            "list", "search",
            "srsearch", input,
            "format", "json");

    String queryString =
        params.entrySet().stream()
            .map(
                entry ->
                    URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8)
                        + "="
                        + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
            .collect(Collectors.joining("&"));

    try {

      // Build HttpRequest
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(ENDPOINT + queryString))
              .GET()
              .timeout(Duration.ofSeconds(config.getTimeout()))
              .build();

      // Send the request and handle the response
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      // Parse the JSON response
      ObjectMapper mapper = new ObjectMapper();
      JsonNode rootNode = mapper.readTree(response.body());
      JsonNode snippetNode = rootNode.path("query").path("search").get(0).path("snippet");

      return snippetNode.asText();
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to communicate with server", e);
      return e.getMessage();
    } catch (InterruptedException e) {
      logger.log(Level.SEVERE, "Communication interrupted", e);
      return e.getMessage();
    }
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public String example() {
    return String.format("%s: Albert Einstein", name());
  }

  @Override
  public String description() {
    return "Returns the Wikipedia page summary for the given search term. Useful for retrieval of historical information.";
  }
}
