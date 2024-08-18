package com.onepointltd.tools;

import com.onepointltd.config.Config;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class SerpAPITool extends HttpTool {

  private static final String SERP_API_ENDPOINT = "https://serpapi.com/search.json";

  public static final String NAME = "serp_api_tool";

  private final Config config;

  public SerpAPITool(Config config) {
    this.config = config;
  }

  @Override
  public String execute(String input) {
    HttpClient client = buildHttpClient();
    String url =
        String.format(
            "%s?q=%s&location=%s&hl=%s&gl=%s&google_domain=google.com&api_key=%s",
            SERP_API_ENDPOINT,
            encode(input),
            encode(config.getSerpApiLocation()),
            config.getSerpApiLanguageCode(),
            config.getSerpApiGeoLocation(),
            config.getSerpApiKey());
    HttpRequest request = buildRequest(url, config);
    return handleResponse(
        client,
        request,
        (responseBody) -> {
          return responseBody;
        });
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public String example() {
    return "serp_api_tool: weather tomorrow in London is sunny with temperature 20 degrees";
  }

  @Override
  public String description() {
    return "Returns the result of a SerpAPI Google search. Useful to get information about current events, weather, news, etc.";
  }
}
