package com.thiagoti.challenge.xgeeks.calendar.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SlotDto {

  private LocalDateTime startDate;

  private LocalDateTime endDate;

}
