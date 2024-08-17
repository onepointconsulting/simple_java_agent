package com.onepointltd.tools;

import static com.onepointltd.config.Logging.logger;

import com.onepointltd.config.Config;
import com.onepointltd.model.DuckDuckGoResult;
import com.onepointltd.model.Serializer;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;

public abstract class HttpTool extends AbstractTool{

  protected HttpClient buildHttpClient() {
    return HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
  }

  protected String encode(String input) {
    return URLEncoder.encode(input, StandardCharsets.UTF_8);
  }

  protected HttpRequest buildRequest(String url, Config config) {
    return HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("User-Agent", "Mozilla/5.0")
            .header("Accept", "*/*")
            .timeout(Duration.ofSeconds(config.getTimeout()))
            .build();
  }

  protected String handleResponse(HttpClient client, HttpRequest request,
      Function<String, String> extractor) {
    try {
      // Send the request and handle the response
      HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

      // Convert the response body to a string using the determined charset
      String responseBody = new String(response.body(), StandardCharsets.UTF_8);

      return extractor.apply(responseBody);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to communicate with server", e);
      return e.getMessage();
    } catch (InterruptedException e) {
      logger.log(Level.SEVERE, "Communication interrupted", e);
      return e.getMessage();
    }
  }
}
