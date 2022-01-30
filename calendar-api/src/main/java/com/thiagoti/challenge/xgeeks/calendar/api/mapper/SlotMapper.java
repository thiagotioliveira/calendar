package com.thiagoti.challenge.xgeeks.calendar.api.mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.DeleteSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SaveSlotBulkItemDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotBulkItemStatus;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;
import com.thiagoti.challenge.xgeeks.calendar.api.model.PersonSlot;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.DeletePersonSlotBulkResponseBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PersonSlotItem;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotBulkRequestBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotBulkResponseBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotRequestBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotResponseBody;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostSearchAvailableResponseBody;

@Mapper
public interface SlotMapper {

  @Named("timeStampToLocalDateTime")
  static LocalDateTime timeStampToLocalDateTime(Timestamp timestamp) {
    return timestamp.toLocalDateTime();
  }

  @Named("statusToPostPersonSlotBulkResponseBodyStatus")
  static com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotBulkResponseBody.StatusEnum statusToPostPersonSlotBulkResponseBodyStatus(
      SlotBulkItemStatus slotBulkItemStatus) {
    return com.thiagoti.challenge.xgeeks.calendar.api.v1.model.PostPersonSlotBulkResponseBody.StatusEnum.fromValue(slotBulkItemStatus.getCode());
  }

  @Named("statusToDeletePersonSlotBulkResponseBodyStatus")
  static com.thiagoti.challenge.xgeeks.calendar.api.v1.model.DeletePersonSlotBulkResponseBody.StatusEnum statusToDeletePersonSlotBulkResponseBodyStatus(
      SlotBulkItemStatus slotBulkItemStatus) {
    return com.thiagoti.challenge.xgeeks.calendar.api.v1.model.DeletePersonSlotBulkResponseBody.StatusEnum.fromValue(slotBulkItemStatus.getCode());
  }

  @Mapping(source = "startDate", target = "startDate", qualifiedByName = "timeStampToLocalDateTime")
  @Mapping(source = "endDate", target = "endDate", qualifiedByName = "timeStampToLocalDateTime")
  PersonSlotItem toPersonSlotItem(PersonSlot personSlot);

  PostPersonSlotResponseBody toPostPersonSlotResponseBody(PersonSlot personSlot);

  @Mapping(source = "status", target = "status", qualifiedByName = "statusToPostPersonSlotBulkResponseBodyStatus")
  @Mapping(source = "slot.startDate", target = "startDate")
  @Mapping(source = "slot.endDate", target = "endDate")
  PostPersonSlotBulkResponseBody toPostPersonSlotBulkResponseBody(SaveSlotBulkItemDto dto);

  @Mapping(source = "status", target = "status", qualifiedByName = "statusToDeletePersonSlotBulkResponseBodyStatus")
  DeletePersonSlotBulkResponseBody toDeletePersonSlotBulkResponseBody(DeleteSlotBulkItemDto dto);

  PostSearchAvailableResponseBody toPostSearchAvailableResponseBody(SlotDto dto);

  SlotDto toSlotDto(PostPersonSlotRequestBody postPersonSlotRequestBody);

  SlotDto toSlotDto(PostPersonSlotBulkRequestBody postPersonSlotBulkRequestBody);
}
