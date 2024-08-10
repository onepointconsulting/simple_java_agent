package com.onepointltd.tools;

import com.onepointltd.model.Function;

public class FunctionalTodayTool extends TodayTool implements FunctionalTool {
  @Override
  public Function function() {
    return new Function(super.name(), super.description());
  }
}
