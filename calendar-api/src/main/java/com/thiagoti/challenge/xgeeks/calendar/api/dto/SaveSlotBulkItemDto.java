package com.thiagoti.challenge.xgeeks.calendar.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveSlotBulkItemDto {

  private String id;

  private SlotDto slot;

  private String message;

  private SlotBulkItemStatus status;

}
