package com.thiagoti.challenge.xgeeks.calendar.api.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

  public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static Calendar toCalendar(Timestamp timestamp) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(timestamp.getTime());
    return calendar;
  }

  public static Timestamp toTimestamp(LocalDateTime datetime) {
    return Timestamp.valueOf(datetime);
  }

  public static LocalDateTime toLocalDateTime(String dateString, String pattern) {
    return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(pattern));
  }

}
