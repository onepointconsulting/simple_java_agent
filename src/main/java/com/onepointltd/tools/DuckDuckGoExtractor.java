package com.onepointltd.tools;

import com.onepointltd.model.DuckDuckGoResult;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DuckDuckGoExtractor {

  public static List<DuckDuckGoResult> extract(String html) {
    Document doc = Jsoup.parse(html);

    // Select elements containing the headlines
    Elements resultBody = doc.select("div.result__body");

    List<DuckDuckGoResult> results = new ArrayList<>();

    // Extract and print the headlines
    for (Element bodyElement : resultBody) {
      Element headlineElement = bodyElement.selectFirst("h2.result__title a.result__a");
      String title = headlineElement.text();
      Element urlElement = bodyElement.selectFirst(".result__url");
      String url = urlElement.text();
      Element snippetElement = bodyElement.selectFirst(".result__snippet");
      String snippet = snippetElement.text();
      results.add(new DuckDuckGoResult(title, url, snippet));
    }
    return results;
  }
}
