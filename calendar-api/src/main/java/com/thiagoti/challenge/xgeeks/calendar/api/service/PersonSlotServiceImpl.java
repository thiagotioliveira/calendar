package com.thiagoti.challenge.xgeeks.calendar.api.service;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.toTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.DeleteSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.Messages;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SaveSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotBulkItemStatus;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.ValidatorDto;
import com.thiagoti.challenge.xgeeks.calendar.api.model.PersonSlot;
import com.thiagoti.challenge.xgeeks.calendar.api.repository.PersonSlotRepository;
import com.thiagoti.challenge.xgeeks.calendar.api.service.exception.BussinesRuleException;
import com.thiagoti.challenge.xgeeks.calendar.api.service.validation.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonSlotServiceImpl implements PersonSlotService {

  private final Validator checkIfExistsPersonValidator;

  private final Validator checkIfIsValidSlotValidator;

  private final PersonSlotRepository repository;

  @Override
  public List<PersonSlot> getByPerson(String personId) {
    return getByPerson(personId, Optional.empty(), Optional.empty());
  }

  @Override
  public List<PersonSlot> getByPerson(String personId, Optional<LocalDateTime> slotDateFrom, Optional<LocalDateTime> slotDateTo) {
    log.debug("getByPerson - {} - {} - {}", personId, slotDateFrom, slotDateTo);
    checkIfExistsPersonValidator.doValidate(ValidatorDto.builder().personId(personId).build());

    List<PersonSlot> result = null;

    if (slotDateFrom.isPresent() && slotDateTo.isPresent()) {
      result = repository.findByPersonIdAndStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByStartDate(personId, toTimestamp(slotDateFrom.get()),
          toTimestamp(slotDateTo.get()));
    } else if (slotDateFrom.isPresent()) {
      result = repository.findByPersonIdAndStartDateGreaterThanEqualOrderByStartDate(personId, toTimestamp(slotDateFrom.get()));
    } else if (slotDateTo.isPresent()) {
      result = repository.findByPersonIdAndEndDateLessThanEqualOrderByStartDate(personId, toTimestamp(slotDateTo.get()));
    } else {
      result = repository.findByPersonIdOrderByStartDate(personId);
    }

    return result;
  }

  @Override
  public PersonSlot save(String personId, SlotDto slot) {
    checkIfExistsPersonValidator.doValidate(ValidatorDto.builder().personId(personId).build());
    checkIfIsValidSlotValidator.doValidate(ValidatorDto.builder().slot(slot).build());

    PersonSlot personSlot = new PersonSlot();
    personSlot.setPersonId(personId);
    personSlot.setStartDate(toTimestamp(slot.getStartDate()));
    personSlot.setEndDate(toTimestamp(slot.getEndDate()));

    return repository.save(personSlot);
  }

  @Override
  public List<SaveSlotBulkItemDto> saveBulk(String personId, Set<SlotDto> slots) {
    List<SaveSlotBulkItemDto> result = new ArrayList<>();
    slots.forEach(s -> {
      SaveSlotBulkItemDto itemBulk = new SaveSlotBulkItemDto();
      itemBulk.setSlot(s);
      try {
        itemBulk.setId(save(personId, s).getId());
        itemBulk.setMessage(Messages.CREATED.getMessage());
        itemBulk.setStatus(SlotBulkItemStatus._201);
      } catch (BussinesRuleException e) {
        itemBulk.setStatus(SlotBulkItemStatus._400);
        itemBulk.setMessage(e.getMessage());
      } catch (DataIntegrityViolationException e) {
        itemBulk.setStatus(SlotBulkItemStatus._400);
        itemBulk.setMessage(Messages.SLOT_ALREADY_REGISTERED.getMessage());
      } catch (Exception e) {
        itemBulk.setStatus(SlotBulkItemStatus._500);
        itemBulk.setMessage(Messages.GENERIC_ERROR_MESSAGE.getMessage());
      }
      result.add(itemBulk);
    });
    return result;
  }

  @Override
  public void delete(String personId, String personSlotId) {
    checkIfExistsPersonValidator.doValidate(ValidatorDto.builder().personId(personId).build());
    repository.delete(getByIdAndPersonId(personSlotId, personId));
  }

  @Override
  public List<DeleteSlotBulkItemDto> deleteBulk(String personId, Set<String> personSlotIds) {
    List<DeleteSlotBulkItemDto> result = new ArrayList<>();
    personSlotIds.forEach(personSlotId -> {
      DeleteSlotBulkItemDto itemBulk = new DeleteSlotBulkItemDto();
      itemBulk.setId(personSlotId);
      try {
        delete(personId, personSlotId);
        itemBulk.setMessage(Messages.DELETED.getMessage());
        itemBulk.setStatus(SlotBulkItemStatus._204);
      } catch (BussinesRuleException e) {
        itemBulk.setMessage(e.getMessage());
        itemBulk.setStatus(SlotBulkItemStatus._400);
      } catch (HttpClientErrorException e) {
        itemBulk.setMessage(e.getStatusText());
        itemBulk.setStatus(SlotBulkItemStatus.valueOf(e.getStatusCode().value()));
      } catch (Exception e) {
        itemBulk.setMessage(Messages.GENERIC_ERROR_MESSAGE.getMessage());
        itemBulk.setStatus(SlotBulkItemStatus._500);
      }
      result.add(itemBulk);
    });
    return result;
  }

  @Override
  public PersonSlot getByIdAndPersonId(String personSlotId, String personId) {
    checkIfExistsPersonValidator.doValidate(ValidatorDto.builder().personId(personId).build());
    return repository.findByIdAndPersonId(personSlotId, personId).orElseThrow(() -> new BussinesRuleException(Messages.SLOT_NOT_FOUND.getMessage()));
  }

}
