package com.thiagoti.challenge.xgeeks.calendar.api.service.validation;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.toCalendar;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.toTimestamp;

import java.time.LocalDateTime;
import java.util.Calendar;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.ValidatorDto;
import com.thiagoti.challenge.xgeeks.calendar.api.service.exception.BussinesRuleException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckIfIsValidSlotValidator implements Validator {

  private final Integer startMinuteTarget;

  private final Integer startSecondTarget;

  private final Integer endMinuteTarget;

  private final Integer endSecondTarget;

  private final Integer slotInHour;

  @Override
  public void doValidate(ValidatorDto input) {

    if (input.getSlot().getStartDate().isBefore(LocalDateTime.now())) {
      throw new BussinesRuleException("is not an available slot");
    }

    Calendar startCalendar = toCalendar(toTimestamp(input.getSlot().getStartDate()));

    Integer hourStart = startCalendar.get(Calendar.HOUR);
    Integer minuteStart = startCalendar.get(Calendar.MINUTE);
    Integer secondStart = startCalendar.get(Calendar.SECOND);

    //@formatter:off
    if (isDifferent(this.startMinuteTarget, minuteStart) 
        || isDifferent(this.startSecondTarget, secondStart)) {
      throw new BussinesRuleException(
          String.format("invalid slot, only allowed slots with start date minute equal to %s, seconds equal to %s", 
              startMinuteTarget, startSecondTarget));
    }
    //@formatter:on

    Calendar endCalendar = toCalendar(toTimestamp(input.getSlot().getEndDate()));

    Integer hourEnd = endCalendar.get(Calendar.HOUR);
    Integer minuteEnd = endCalendar.get(Calendar.MINUTE);
    Integer secondEnd = endCalendar.get(Calendar.SECOND);

    //@formatter:off
    if (isDifferent(this.endMinuteTarget, minuteEnd) 
        || isDifferent(this.endSecondTarget, secondEnd)) {
      throw new BussinesRuleException(
          String.format("invalid slot, only allowed slots with end date minute equal to %s, seconds equal to %s", 
              endMinuteTarget, endSecondTarget));
    }
    //@formatter:on

    if (Boolean.FALSE.equals(hourStart.equals(hourEnd))) {
      throw new BussinesRuleException(String.format("invalid slot, a slot must have an interval of %s hour", slotInHour));
    }
  }

  private boolean isDifferent(Integer i, Integer j) {
    return Boolean.FALSE.equals(i.equals(j));
  }

}
