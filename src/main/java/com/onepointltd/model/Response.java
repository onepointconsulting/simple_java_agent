package com.onepointltd.model;

import java.util.Map;

public record Response(int statusCode, String body, Map<String, Object> parsedBody) {
  public Response {
    if (statusCode <= 0) {
      throw new IllegalArgumentException(String.format("Invalid status code: %d", statusCode));
    }
  }
}
