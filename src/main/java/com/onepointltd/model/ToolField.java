package com.onepointltd.model;

public record ToolField(Function function, String type) {
  public ToolField(Function function) {
    this(function, "function");
  }
}
