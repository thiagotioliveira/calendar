package com.thiagoti.challenge.xgeeks.calendar.api.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class TestUtil {

  public static final String MOCK_TEXT = "mock";

  public static String readValueFromFile(String pathToFile) {
    try {
      return IOUtils.resourceToString(pathToFile, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read resource file");
    }
  }
  
  public static void main(String[] args) {
    System.out.println(618 + 95 + 38 + 18 + 190 + 100 + 60 + 300 + 30 + 312);
  }

}
