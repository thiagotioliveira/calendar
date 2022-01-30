package com.thiagoti.challenge.xgeeks.calendar.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonClient;
import com.thiagoti.challenge.xgeeks.calendar.api.service.validation.CheckIfExistsPersonValidator;
import com.thiagoti.challenge.xgeeks.calendar.api.service.validation.CheckIfExistsValidPeopleForAvailableCommonSlotsValidator;
import com.thiagoti.challenge.xgeeks.calendar.api.service.validation.CheckIfIsValidSlotValidator;

@Configuration
public class ValidatorConfig {

  @Bean
  public CheckIfExistsValidPeopleForAvailableCommonSlotsValidator checkIfExistsValidPeopleForAvailableCommonSlotsValidator(
      @Value("${app.validator.checkIfExistsValidPeopleForAvailableCommonSlotsValidator.candidateMin:1}") Integer candidateMin,
      @Value("${app.validator.checkIfExistsValidPeopleForAvailableCommonSlotsValidator.interviewerMin:1}") Integer interviewerMin, PersonClient personClient) {
    return new CheckIfExistsValidPeopleForAvailableCommonSlotsValidator(candidateMin, interviewerMin, personClient);
  }

  @Bean
  public CheckIfIsValidSlotValidator checkIfIsValidSlotValidator(
      @Value("${app.validator.checkIfIsValidSlotValidator.startMinuteTarget:0}") Integer startMinuteTarget,
      @Value("${app.validator.checkIfIsValidSlotValidator.startSecondTarget:0}") Integer startSecondTarget,
      @Value("${app.validator.checkIfIsValidSlotValidator.endMinuteTarget:59}") Integer endMinuteTarget,
      @Value("${app.validator.checkIfIsValidSlotValidator.endSecondTarget:59}") Integer endSecondTarget,
      @Value("${app.validator.checkIfIsValidSlotValidator.slotInHour:1}") Integer slotInHour) {
    return new CheckIfIsValidSlotValidator(startMinuteTarget, startSecondTarget, endMinuteTarget, endSecondTarget, slotInHour);
  }

  @Bean
  public CheckIfExistsPersonValidator checkIfExistsPersonValidator(PersonClient personClient) {
    return new CheckIfExistsPersonValidator(personClient);
  }

}
