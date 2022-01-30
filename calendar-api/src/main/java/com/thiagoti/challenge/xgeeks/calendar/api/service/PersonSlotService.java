package com.thiagoti.challenge.xgeeks.calendar.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.DeleteSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SaveSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;
import com.thiagoti.challenge.xgeeks.calendar.api.model.PersonSlot;

public interface PersonSlotService {

  List<PersonSlot> getByPerson(String personId);

  List<PersonSlot> getByPerson(String personId, Optional<LocalDateTime> slotDateFrom, Optional<LocalDateTime> slotDateTo);

  PersonSlot getByIdAndPersonId(String personSlotId, String personId);

  PersonSlot save(String personId, SlotDto slot);

  List<SaveSlotBulkItemDto> saveBulk(String personId, Set<SlotDto> slots);

  void delete(String personId, String personSlotId);

  List<DeleteSlotBulkItemDto> deleteBulk(String personId, Set<String> personSlotIds);

}
