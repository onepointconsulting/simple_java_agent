package com.onepointltd.tools;

public interface Tool {

  String execute(String input);

  String name();

  String example();

  String description();

  String createExample();
}
