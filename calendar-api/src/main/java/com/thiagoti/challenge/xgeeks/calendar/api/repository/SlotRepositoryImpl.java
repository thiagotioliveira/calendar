package com.thiagoti.challenge.xgeeks.calendar.api.repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SlotRepositoryImpl implements SlotRepository {

  private static final int START_DATE = 0;

  private static final int END_DATE = 1;

  private final EntityManager entityManager;

  @Override
  public List<SlotDto> findAvailableCommonSlotsByPeopleOrderBySlot(Collection<String> personIds) {
    //@formatter:off
    Query query = entityManager.createNativeQuery("select"
        + "        t.start_date, t.end_date "
        + "    from"
        + "        (select"
        + "            count(*) as sum,"
        + "            s.start_date, s.end_date "
        + "        from"
        + "            person_slot s  "
        + "        where"
        + "            s.start_date > CURRENT_TIMESTAMP() "
        + "            and    s.person_id in ("
        + "                :people"
        + "            )  "
        + "        group by"
        + "            s.start_date, s.end_date) t "
        + "    where"
        + "        t.sum = :peopleSize"
        + "    order by t.start_date");
    
    query.setParameter("people", personIds);
    query.setParameter("peopleSize", personIds.size());
    //@formatter:on
    List<Object[]> resultList = query.getResultList();
    
    return resultList.stream().map(i -> new SlotDto(((Timestamp) i[START_DATE]).toLocalDateTime(), ((Timestamp) i[END_DATE]).toLocalDateTime()))
        .collect(Collectors.toList());
  }

}
