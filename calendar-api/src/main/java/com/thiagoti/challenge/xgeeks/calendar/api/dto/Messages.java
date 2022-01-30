package com.thiagoti.challenge.xgeeks.calendar.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {
  //@formatter:off
  CREATED("created"),
  SLOT_ALREADY_REGISTERED("slot already registered"),
  SLOT_NOT_FOUND("slot not found"),
  DELETED("deleted"),
  PERSON_NOT_FOUND("person not found"),
  GENERIC_ERROR_MESSAGE("could not perform an operation");
  //@formatter:on

  private String message;

}
