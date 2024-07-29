package com.onepointltd.tools;

import static com.onepointltd.config.Logging.logger;

import com.onepointltd.config.Config;
import com.onepointltd.model.DuckDuckGoResult;
import com.onepointltd.model.MessagesSerializer;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

public class DuckDuckGo extends AbstractTool {

  private static final String DUCKDUCKGO_ENDPOINT = "https://duckduckgo.com/html/";

  private final Config config;

  public DuckDuckGo(Config config) {
    this.config = config;
  }

  @Override
  public String execute(String input) {
    HttpClient client = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build();
    String url = DUCKDUCKGO_ENDPOINT + "?q=" + URLEncoder.encode(input, StandardCharsets.UTF_8);
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("User-Agent", "Mozilla/5.0")
        .header("Accept", "*/*")
        .timeout(Duration.ofSeconds(config.getTimeout()))
        .build();
    try {
      // Send the request and handle the response
      HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

      // Convert the response body to a string using the determined charset
      String responseBody = new String(response.body(), StandardCharsets.UTF_8);

      List<DuckDuckGoResult> results = DuckDuckGoExtractor.extract(responseBody);
      return MessagesSerializer.toJson(results);
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
    return "duckduckgo";
  }

  @Override
  public String example() {
    return "duckduckgo: weather tomorrow in London";
  }

  @Override
  public String description() {
    return "Returns the result of a DuckDuckGo search.";
  }


}
