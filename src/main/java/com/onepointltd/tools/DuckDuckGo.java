package com.onepointltd.tools;

import com.onepointltd.config.Config;
import com.onepointltd.model.DuckDuckGoResult;
import com.onepointltd.model.Serializer;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

public class DuckDuckGo extends HttpTool {

  public static final String NAME = "duckduckgo";

  private static final String DUCKDUCKGO_ENDPOINT = "https://duckduckgo.com/html/";

  private final Config config;

  public DuckDuckGo(Config config) {
    this.config = config;
  }

  @Override
  public String execute(String input) {
    HttpClient client = buildHttpClient();
    String url = String.format("%s?q=%s", DUCKDUCKGO_ENDPOINT, encode(input));
    HttpRequest request = buildRequest(url, config);
    return handleResponse(
        client,
        request,
        (responseBody) -> {
          List<DuckDuckGoResult> results = DuckDuckGoExtractor.extract(responseBody);
          return Serializer.toJson(results);
        });
  }

  @Override
  public String name() {
    return NAME;
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
