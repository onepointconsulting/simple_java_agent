package com.onepointltd.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TodayToolTest {

  private final TodayTool todayTool = new TodayTool();

  @Test
  void whenExecute_ShouldReturnToday() {
    String today = todayTool.execute(null);
    assertNotNull(today);
  }
}