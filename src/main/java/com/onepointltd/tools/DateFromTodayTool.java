package com.onepointltd.tools;

import java.time.LocalDate;

public class DateFromTodayTool extends AbstractTool {

  public static final String NAME = "Date_from_today";

  @Override
  public String execute(String input) {
    try {
      int diff = Integer.parseInt(input);
      LocalDate today = LocalDate.now();

      // Calculate tomorrow's date
      LocalDate localDate = today.plusDays(diff);
      return localDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (NumberFormatException e) {
      return "Invalid input. Please enter a number.";
    }
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public String example() {
    return "Date_from_today: 2024-10-01";
  }

  @Override
  public String description() {
    return "This tool gives you the date that is a certain number of days from today in the format YYYY-MM-DD. It expects a number as input.";
  }
}
