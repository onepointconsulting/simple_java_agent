package com.onepointltd.config;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {

  public static final Logger logger = Logger.getLogger(Logging.class.getName());

  public static void initLogging() {
    try {
      Handler fileHandler = new FileHandler("simple_agent.log", true); // Append to existing file
      fileHandler.setFormatter(new SimpleFormatter());
      logger.addHandler(fileHandler);

      // Set the logging level to FINEST to capture all log levels
      logger.setLevel(Level.FINE);
      fileHandler.setLevel(Level.FINE);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Failed to create log file");
    }
  }
}
