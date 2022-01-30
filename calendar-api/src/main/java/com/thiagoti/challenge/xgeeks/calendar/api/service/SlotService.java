package com.thiagoti.challenge.xgeeks.calendar.api.service;

import java.util.List;
import java.util.Set;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;

public interface SlotService {

  List<SlotDto> getAvailableCommonSlotsByPeopleOrderBySlot(Set<String> peopleIds);

}
