package com.thiagoti.challenge.xgeeks.calendar.api.repository;

import java.util.Collection;
import java.util.List;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;

public interface SlotRepository {

  List<SlotDto> findAvailableCommonSlotsByPeopleOrderBySlot(Collection<String> personIds);
}
