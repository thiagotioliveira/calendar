package com.thiagoti.challenge.xgeeks.calendar.api.service;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.PATTERN;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.toLocalDateTime;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.toTimestamp;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INES_ID;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.TestUtil.MOCK_TEXT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.DeleteSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SaveSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotBulkItemStatus;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;
import com.thiagoti.challenge.xgeeks.calendar.api.model.PersonSlot;
import com.thiagoti.challenge.xgeeks.calendar.api.repository.PersonSlotRepository;
import com.thiagoti.challenge.xgeeks.calendar.api.service.exception.BussinesRuleException;
import com.thiagoti.challenge.xgeeks.calendar.api.service.validation.Validator;

public class PersonSlotServiceImplTest {

  private final String START_DATE = "2099-12-05 10:00:00";

  private final String END_DATE = "2099-12-05 10:59:59";

  private Validator checkIfExistsPersonValidator;

  private Validator checkIfIsValidSlotValidator;

  private PersonSlotRepository repository;

  public PersonSlotServiceImplTest() {
    this.checkIfExistsPersonValidator = mock(Validator.class);
    this.checkIfIsValidSlotValidator = mock(Validator.class);
    this.repository = mock(PersonSlotRepository.class);
  }

  @Test
  void getByPersonSendingStartAndEndDateTest() {
    List<PersonSlot> expectedList = List
        .of(new PersonSlot(MOCK_TEXT, INES_ID, toTimestamp(toLocalDateTime(START_DATE, PATTERN)), toTimestamp(toLocalDateTime(END_DATE, PATTERN))));
    when(repository.findByPersonIdAndStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByStartDate(any(), any(), any())).thenReturn(expectedList);

    List<PersonSlot> resultList = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).getByPerson(INES_ID,
        Optional.of(toLocalDateTime(START_DATE, PATTERN)), Optional.of(toLocalDateTime(START_DATE, PATTERN)));

    assertEquals(expectedList, resultList);
  }
  
  @Test
  void getByPersonSendingStartDateTest() {
    List<PersonSlot> expectedList = List
        .of(new PersonSlot(MOCK_TEXT, INES_ID, toTimestamp(toLocalDateTime(START_DATE, PATTERN)), toTimestamp(toLocalDateTime(END_DATE, PATTERN))));
    when(repository.findByPersonIdAndStartDateGreaterThanEqualOrderByStartDate(any(), any())).thenReturn(expectedList);

    List<PersonSlot> resultList = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).getByPerson(INES_ID,
        Optional.of(toLocalDateTime(START_DATE, PATTERN)), Optional.empty());

    assertEquals(expectedList, resultList);
  }

  @Test
  void getByPersonSendingEndDateTest() {
    List<PersonSlot> expectedList = List
        .of(new PersonSlot(MOCK_TEXT, INES_ID, toTimestamp(toLocalDateTime(START_DATE, PATTERN)), toTimestamp(toLocalDateTime(END_DATE, PATTERN))));
    when(repository.findByPersonIdAndEndDateLessThanEqualOrderByStartDate(any(), any())).thenReturn(expectedList);

    List<PersonSlot> resultList = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).getByPerson(INES_ID,
        Optional.empty(), Optional.of(toLocalDateTime(START_DATE, PATTERN)));

    assertEquals(expectedList, resultList);
  }

  @Test
  void getByPersonTest() {
    List<PersonSlot> expectedList = List
        .of(new PersonSlot(MOCK_TEXT, INES_ID, toTimestamp(toLocalDateTime(START_DATE, PATTERN)), toTimestamp(toLocalDateTime(END_DATE, PATTERN))));
    when(repository.findByPersonIdOrderByStartDate(any())).thenReturn(expectedList);

    List<PersonSlot> resultList = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).getByPerson(INES_ID,
        Optional.empty(), Optional.empty());

    assertEquals(expectedList, resultList);
  }

  @Test
  void saveBulkCreatedTest() {
    final SlotDto slot = new SlotDto(toLocalDateTime(START_DATE, PATTERN), toLocalDateTime(END_DATE, PATTERN));

    PersonSlot expected = new PersonSlot();
    expected.setId(MOCK_TEXT);
    expected.setPersonId(INES_ID);
    expected.setStartDate(toTimestamp(slot.getStartDate()));
    expected.setEndDate(toTimestamp(slot.getEndDate()));

    when(repository.save(any())).thenReturn(expected);
    
    List<SaveSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).saveBulk(INES_ID, Set.of(slot));

    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._201, result.get(0).getStatus());
  }

  @Test
  void saveBulkBussinesRuleErrorTest() {
    final SlotDto slot = new SlotDto(toLocalDateTime(START_DATE, PATTERN), toLocalDateTime(END_DATE, PATTERN));
    when(repository.save(any())).thenThrow(new BussinesRuleException(MOCK_TEXT));

    List<SaveSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).saveBulk(INES_ID,
        Set.of(slot));

    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._400, result.get(0).getStatus());
  }

  @Test
  void saveBulkDataIntegrityViolationErrorTest() {
    final SlotDto slot = new SlotDto(toLocalDateTime(START_DATE, PATTERN), toLocalDateTime(END_DATE, PATTERN));
    when(repository.save(any())).thenThrow(new DataIntegrityViolationException(MOCK_TEXT));

    List<SaveSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).saveBulk(INES_ID,
        Set.of(slot));

    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._400, result.get(0).getStatus());
  }

  @Test
  void saveBulkErrorTest() {
    final SlotDto slot = new SlotDto(toLocalDateTime(START_DATE, PATTERN), toLocalDateTime(END_DATE, PATTERN));
    when(repository.save(any())).thenThrow(new RuntimeException(MOCK_TEXT));

    List<SaveSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).saveBulk(INES_ID,
        Set.of(slot));

    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._500, result.get(0).getStatus());
  }

  @Test
  void deleteBulkDeletedTest() {
    final SlotDto slot = new SlotDto(toLocalDateTime(START_DATE, PATTERN), toLocalDateTime(END_DATE, PATTERN));

    PersonSlot personSlot = new PersonSlot();
    personSlot.setId(MOCK_TEXT);
    personSlot.setPersonId(INES_ID);
    personSlot.setStartDate(toTimestamp(slot.getStartDate()));
    personSlot.setEndDate(toTimestamp(slot.getEndDate()));

    when(repository.findByIdAndPersonId(any(), any())).thenReturn(Optional.of(personSlot));

    List<DeleteSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).deleteBulk(INES_ID,
        Set.of(MOCK_TEXT));
    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._204, result.get(0).getStatus());
  }

  @Test
  void deleteBulkBussinesRuleErrorTest() {
    when(repository.findByIdAndPersonId(any(), any())).thenThrow(new BussinesRuleException(MOCK_TEXT));

    List<DeleteSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).deleteBulk(INES_ID,
        Set.of(MOCK_TEXT));
    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._400, result.get(0).getStatus());
  }

  @Test
  void deleteHttpClientErrorExceptionErrorTest() {
    when(repository.findByIdAndPersonId(any(), any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

    List<DeleteSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).deleteBulk(INES_ID,
        Set.of(MOCK_TEXT));
    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._404, result.get(0).getStatus());
  }

  @Test
  void deleteErrorTest() {
    when(repository.findByIdAndPersonId(any(), any())).thenThrow(new RuntimeException(MOCK_TEXT));

    List<DeleteSlotBulkItemDto> result = new PersonSlotServiceImpl(checkIfExistsPersonValidator, checkIfIsValidSlotValidator, repository).deleteBulk(INES_ID,
        Set.of(MOCK_TEXT));
    assertEquals(1, result.size());
    assertEquals(SlotBulkItemStatus._500, result.get(0).getStatus());
  }

}
