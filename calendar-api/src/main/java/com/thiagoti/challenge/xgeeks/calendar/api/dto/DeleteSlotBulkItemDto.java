package com.thiagoti.challenge.xgeeks.calendar.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteSlotBulkItemDto {

  private String id;

  private String message;

  private SlotBulkItemStatus status;

}
