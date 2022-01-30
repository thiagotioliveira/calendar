package com.thiagoti.challenge.xgeeks.calendar.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SlotBulkItemStatus {
  
  _201("201", "created"), _204("204", "executed"), _400("400", "bussines rule error"), _404("404", "not found"), _500("500", "internal error");

  private String code;

  private String message;

  public static SlotBulkItemStatus valueOf(Integer code) {
    return SlotBulkItemStatus.valueOf("_" + code);
  }

}
