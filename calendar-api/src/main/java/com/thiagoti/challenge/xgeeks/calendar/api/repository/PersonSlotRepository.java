package com.thiagoti.challenge.xgeeks.calendar.api.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thiagoti.challenge.xgeeks.calendar.api.model.PersonSlot;

@Repository
public interface PersonSlotRepository extends CrudRepository<PersonSlot, String> {

  List<PersonSlot> findByPersonIdOrderByStartDate(String personId);

  List<PersonSlot> findByPersonIdAndStartDateGreaterThanEqualOrderByStartDate(String personId, Timestamp slotDateFrom);

  List<PersonSlot> findByPersonIdAndEndDateLessThanEqualOrderByStartDate(String personId, Timestamp slotDateTo);

  List<PersonSlot> findByPersonIdAndStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByStartDate(String personId, Timestamp slotDateFrom, Timestamp slotDateTo);

  Optional<PersonSlot> findByIdAndPersonId(String id, String personId);

}
