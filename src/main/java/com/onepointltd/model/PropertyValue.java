package com.onepointltd.model;

public record PropertyValue(String description, String type, Items items) {

  public PropertyValue(String description, String type) {
    this(description, type, null);
  }
}
