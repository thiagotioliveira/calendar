package com.thiagoti.challenge.xgeeks.calendar.api.service.validation;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.ValidatorDto;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.Person;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonClient;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonRole;
import com.thiagoti.challenge.xgeeks.calendar.api.service.exception.BussinesRuleException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckIfExistsValidPeopleForAvailableCommonSlotsValidator implements Validator {

  private final Integer candidateMin;

  private final Integer interviewerMin;

  private final PersonClient personClient;

  @Override
  public void doValidate(ValidatorDto input) {

    Set<Person> people = input.getPeopleIds().stream().map(personId -> {
      try {
        return personClient.getById(personId);
      } catch (Exception e) {
        return null;
      }
    }).filter(personId -> Objects.nonNull(personId)).collect(Collectors.toSet());

    long candidatesCount = people.stream().filter(p -> PersonRole.CANDIDATE.equals(p.getType())).count();
    long interviewersCount = people.stream().filter(p -> PersonRole.INTERVIEWER.equals(p.getType())).count();
    
    if (candidatesCount < candidateMin || interviewersCount < interviewerMin) {
      throw new BussinesRuleException(
          String.format("In the list of people there must be at least %s candidate and %s interviewer.", candidateMin, interviewerMin));
    }

  }

}
