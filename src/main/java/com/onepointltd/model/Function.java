package com.onepointltd.model;

public class Function {

  private final String name;

  private final String description;

  private Parameters parameters;

  public Function(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setParameters(Parameters parameters) {
    this.parameters = parameters;
  }

  public Parameters getParameters() {
    return parameters;
  }
}
