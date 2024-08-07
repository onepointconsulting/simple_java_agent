package com.onepointltd.tools;

import java.time.LocalDate;

public class TodayTool extends AbstractTool {

  @Override
  public String execute(String input) {
    LocalDate today = LocalDate.now();
    return today.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  @Override
  public String name() {
    return "Today";
  }

  @Override
  public String example() {
    return "Today: 2021-10-01";
  }

  @Override
  public String description() {
    return "This tool gives you today's date in the format YYYY-MM-DD.";
  }
}
