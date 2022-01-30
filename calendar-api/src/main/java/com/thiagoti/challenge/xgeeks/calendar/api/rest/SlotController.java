package com.thiagoti.challenge.xgeeks.calendar.api.rest;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thiagoti.challenge.xgeeks.calendar.api.mapper.SlotMapper;
import com.thiagoti.challenge.xgeeks.calendar.api.service.PersonSlotService;
import com.thiagoti.challenge.xgeeks.calendar.api.service.SlotService;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.SlotApi;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.DeletePersonSlotBulkResponseBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PersonSlotItem;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotBulkRequestBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotBulkResponseBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotRequestBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotResponseBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostSearchAvailableResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SlotController implements SlotApi {

  private final SlotService slotService;

  private final PersonSlotService personSlotService;

  private final SlotMapper mapper;

  @Override
  public ResponseEntity<List<PostSearchAvailableResponseBody>> postSearchAvailableSlots(
      @Valid List<String> personIds) {
    log.info("postSearchAvailableSlots people ids {}", personIds);
    List<PostSearchAvailableResponseBody> response = slotService.getAvailableCommonSlotsByPeopleOrderBySlot(new HashSet<>(personIds)).stream()
        .map(i -> mapper.toPostSearchAvailableResponseBody(i)).collect(Collectors.toList());
    log.info("return ok - {}", response);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<List<PersonSlotItem>> getSlotsByPerson(String personId, @Valid LocalDateTime slotDateFrom, @Valid LocalDateTime slotDateTo) {
    log.info("getSlotsByPerson - {}", personId);
    List<PersonSlotItem> response = personSlotService.getByPerson(personId, Optional.ofNullable(slotDateFrom), Optional.ofNullable(slotDateTo)).stream()
        .map(s -> mapper.toPersonSlotItem(s))
        .collect(Collectors.toList());
    log.info("return ok - {}", response);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<PostPersonSlotResponseBody> postSlotByPerson(String personId, @Valid PostPersonSlotRequestBody slot) {
    log.info("postSlotByPerson - {} - {}", personId, slot);
    return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toPostPersonSlotResponseBody(personSlotService.save(personId, mapper.toSlotDto(slot))));
  }

  @Override
  public ResponseEntity<List<PostPersonSlotBulkResponseBody>> postSlotByPersonBulk(String personId, @Valid List<PostPersonSlotBulkRequestBody> slots) {
    log.info("postSlotByPersonBulk - {} - {}", personId, slots);

    return ResponseEntity.status(HttpStatus.MULTI_STATUS)
        .body(personSlotService.saveBulk(personId, slots.stream().map(s -> mapper.toSlotDto(s)).collect(Collectors.toSet())).stream()
            .map(dto -> mapper.toPostPersonSlotBulkResponseBody(dto))
            .collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<Void> deletePersonSlotById(String personId, String personSlotId) {
    log.info("deletePersonSlotById personId {}, personSlotId {}", personId, personSlotId);
    personSlotService.delete(personId, personSlotId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<List<DeletePersonSlotBulkResponseBody>> deleteSlotByPersonBulk(String personId, @Valid List<String> personSlotIds) {
    log.info("deleteSlotByPersonBulk personId {}, personSlotIds {}", personId, personSlotIds);
    List<DeletePersonSlotBulkResponseBody> response = personSlotService.deleteBulk(personId, new HashSet<>(personSlotIds)).stream()
        .map(dto -> mapper.toDeletePersonSlotBulkResponseBody(dto)).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(response);
  }

}
