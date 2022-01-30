package com.thiagoti.challenge.xgeeks.calendar.api.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.ValidatorDto;
import com.thiagoti.challenge.xgeeks.calendar.api.repository.SlotRepository;
import com.thiagoti.challenge.xgeeks.calendar.api.service.validation.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SlotServiceImpl implements SlotService {

  private final SlotRepository slotRepository;

  private final Validator checkIfExistsValidPeopleForAvailableCommonSlotsValidator;

  @Override
  public List<SlotDto> getAvailableCommonSlotsByPeopleOrderBySlot(Set<String> peopleIds) {
    log.debug("getAvailableSlotsByPeople {} ", peopleIds);

    checkIfExistsValidPeopleForAvailableCommonSlotsValidator.doValidate(ValidatorDto.builder().peopleIds(peopleIds).build());

    return slotRepository.findAvailableCommonSlotsByPeopleOrderBySlot(peopleIds);
  }

}
