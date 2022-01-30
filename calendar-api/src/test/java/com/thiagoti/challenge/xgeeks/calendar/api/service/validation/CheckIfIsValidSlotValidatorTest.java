package com.thiagoti.challenge.xgeeks.calendar.api.service.validation;

import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.PATTERN;
import static com.thiagoti.challenge.xgeeks.calendar.api.util.DateUtils.toLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.SlotDto;
import com.thiagoti.challenge.xgeeks.calendar.api.dto.ValidatorDto;
import com.thiagoti.challenge.xgeeks.calendar.api.service.exception.BussinesRuleException;

public class CheckIfIsValidSlotValidatorTest {

  @Test
  void doValidateSuccess() {
    new CheckIfIsValidSlotValidator(0, 0, 59, 59, 1)
        .doValidate(
            ValidatorDto.builder().slot(new SlotDto(toLocalDateTime("2099-12-05 10:00:00", PATTERN), toLocalDateTime("2099-12-05 10:59:59", PATTERN))).build());
  }

  @Test
  void doValidateSlotUnavailableError() {
    try {
      new CheckIfIsValidSlotValidator(0, 0, 59, 59, 1).doValidate(
          ValidatorDto.builder().slot(new SlotDto(toLocalDateTime("2000-12-05 10:00:00", PATTERN), toLocalDateTime("2000-12-05 10:59:59", PATTERN))).build());
      Assertions.fail();
    } catch (Exception e) {
      assertTrue(e instanceof BussinesRuleException);
    }
  }

  @Test
  void doValidateInvalidSlotError() {
    try {
      new CheckIfIsValidSlotValidator(0, 0, 59, 59, 1).doValidate(
          ValidatorDto.builder().slot(new SlotDto(toLocalDateTime("2099-12-05 10:10:10", PATTERN), toLocalDateTime("2099-12-05 10:50:50", PATTERN))).build());
      Assertions.fail();
    } catch (Exception e) {
      assertTrue(e instanceof BussinesRuleException);
    }
  }

}
