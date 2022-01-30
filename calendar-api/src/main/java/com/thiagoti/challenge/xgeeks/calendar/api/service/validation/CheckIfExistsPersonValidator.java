package com.thiagoti.challenge.xgeeks.calendar.api.service.validation;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.ValidatorDto;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckIfExistsPersonValidator implements Validator {

  private final PersonClient personClient;

  @Override
  public void doValidate(ValidatorDto input) {
    personClient.getById(input.getPersonId());
  }

}
