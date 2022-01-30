package com.thiagoti.challenge.xgeeks.calendar.api.service;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.PersonConstants.INES_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;
import com.thiagoti.challenge.xgeeks.calendar.api.repository.SlotRepository;
import com.thiagoti.challenge.xgeeks.calendar.api.service.validation.Validator;

public class SlotServiceImplTest {

  private SlotRepository slotRepository;

  private Validator checkIfExistsValidPeopleForAvailableCommonSlotsValidator;

  public SlotServiceImplTest() {
    this.slotRepository = mock(SlotRepository.class);
    this.checkIfExistsValidPeopleForAvailableCommonSlotsValidator = mock(Validator.class);
  }

  @Test
  void getAvailableCommonSlotsByPeopleOrderBySlotTest() {
    LocalDateTime now = LocalDateTime.now();
    List<SlotDto> expected = List.of(new SlotDto(LocalDateTime.now(), now));
    when(slotRepository.findAvailableCommonSlotsByPeopleOrderBySlot(any())).thenReturn(expected);
    List<SlotDto> result = new SlotServiceImpl(slotRepository, checkIfExistsValidPeopleForAvailableCommonSlotsValidator)
        .getAvailableCommonSlotsByPeopleOrderBySlot(Set.of(INES_ID));

    assertEquals(expected, result);
  }

}
