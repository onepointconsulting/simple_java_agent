package com.onepointltd.model;

import com.onepointltd.tools.FunctionalTool;
import java.util.Arrays;
import java.util.List;

public class ToolFieldFactory {

  public static List<ToolField> createTools(FunctionalTool[] tools) {
    List<Function> functions = Arrays.stream(tools).map(FunctionalTool::function).toList();
    return functions.stream().map(ToolField::new).toList();
  }
}
