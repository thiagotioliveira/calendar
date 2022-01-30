package com.thiagoti.challenge.xgeeks.calendar.api.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidatorDto {

  private String personId;

  private SlotDto slot;

  private Set<String> peopleIds;

}
