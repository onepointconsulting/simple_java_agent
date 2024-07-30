package com.onepointltd.model;

public enum ParameterType {

  STRING,
  INTEGER,
  BOOLEAN,
  OBJECT,
  ARRAY,
  NULL;

  public static ParameterType fromString(String type) {
    return switch (type) {
      case "string" -> STRING;
      case "integer" -> INTEGER;
      case "boolean" -> BOOLEAN;
      case "object" -> OBJECT;
      case "array" -> ARRAY;
      case "null" -> NULL;
      default -> throw new IllegalArgumentException("Unknown type: " + type);
    };
  }

  public String toString() {
    return switch (this) {
      case STRING -> "string";
      case INTEGER -> "integer";
      case BOOLEAN -> "boolean";
      case OBJECT -> "object";
      case ARRAY -> "array";
      case NULL -> "null";
    };
  }
}
