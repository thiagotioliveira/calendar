package com.thiagoti.challenge.xgeeks.calendar.api.config;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.CARL_ID;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.CARL_NAME;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INES_ID;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INES_NAME;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INGRID_ID;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INGRID_NAME;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.Messages;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.Person;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonClient;
import com.thiagoti.challenge.xgeeks.calendar.api.integration.PersonRole;

@Configuration
public class IntegrationConfig {

  @Bean
  public PersonClient personClient() {
    final PersonClient personClientMock = mock(PersonClient.class);
    when(personClientMock.getById(argThat(i -> Boolean.FALSE.equals(List.of(INES_ID, INGRID_ID, CARL_ID).contains(i)))))
        .thenThrow(getPersonNotFoundException());
    when(personClientMock.getById(INES_ID)).thenReturn(new Person(INES_ID, INES_NAME, PersonRole.INTERVIEWER));
    when(personClientMock.getById(INGRID_ID)).thenReturn(new Person(INGRID_ID, INGRID_NAME, PersonRole.INTERVIEWER));
    when(personClientMock.getById(CARL_ID)).thenReturn(new Person(CARL_ID, CARL_NAME, PersonRole.CANDIDATE));
    return personClientMock;
  }

  private HttpClientErrorException getPersonNotFoundException() {
    return new HttpClientErrorException(HttpStatus.NOT_FOUND, Messages.PERSON_NOT_FOUND.getMessage());
  }

}
