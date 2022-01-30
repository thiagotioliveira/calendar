package com.thiagoti.challenge.xgeeks.calendar.api.service.validation;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.TestUtil.MOCK_TEXT;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.CARL_ID;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.CARL_NAME;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INES_ID;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INES_NAME;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INGRID_ID;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INGRID_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.Messages;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.ValidatorDto;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.Person;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonClient;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonRole;
import com.thiagoti.challenge.xgeeks.calendar.api.service.exception.BussinesRuleException;

public class CheckIfExistsValidPeopleForAvailableCommonSlotsValidatorTest {

  private PersonClient personClient;

  public CheckIfExistsValidPeopleForAvailableCommonSlotsValidatorTest() {
    this.personClient = mock(PersonClient.class);
    when(personClient.getById(argThat(i -> Boolean.FALSE.equals(List.of(INES_ID, INGRID_ID, CARL_ID).contains(i)))))
        .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, Messages.PERSON_NOT_FOUND.getMessage()));
    when(personClient.getById(INES_ID)).thenReturn(new Person(INES_ID, INES_NAME, PersonRole.INTERVIEWER));
    when(personClient.getById(INGRID_ID)).thenReturn(new Person(INGRID_ID, INGRID_NAME, PersonRole.INTERVIEWER));
    when(personClient.getById(CARL_ID)).thenReturn(new Person(CARL_ID, CARL_NAME, PersonRole.CANDIDATE));
  }

  @Test
  void doValidateSuccess() {
    new CheckIfExistsValidPeopleForAvailableCommonSlotsValidator(1, 1, personClient)
        .doValidate(ValidatorDto.builder().peopleIds(Set.of(INES_ID, INGRID_ID, CARL_ID)).build());
  }

  @Test
  void doValidateError() {
    try {
      new CheckIfExistsValidPeopleForAvailableCommonSlotsValidator(1, 1, personClient).doValidate(ValidatorDto.builder().peopleIds(Set.of(MOCK_TEXT)).build());
      Assertions.fail();
    } catch (Exception e) {
      assertTrue(e instanceof BussinesRuleException);
    }
  }

}
