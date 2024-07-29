package com.onepointltd.tools;

public abstract class AbstractTool implements Tool {

  @Override
  public String createExample() {
    return String.format("""
%s:
e.g. %s
%s
""", name(), example(), description());
  }
}
