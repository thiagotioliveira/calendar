package com.thiagoti.challenge.xgeeks.calendar.api.rest.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.thiagoti.challenge.xgeeks.calendar.api.dto.Messages;
import com.thiagoti.challenge.xgeeks.calendar.api.service.exception.BussinesRuleException;
import com.thiagoti.challenge.xgeeks.calendar.api.v1.model.ErrorResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @ExceptionHandler(BussinesRuleException.class)
  @ResponseBody
  public ResponseEntity<Void> handleBussinesRuleException(BussinesRuleException e) {
    log.info("handleBussinesRuleException {}", e.getMessage());
    return new ResponseEntity(new ErrorResponseBody().message(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseBody
  public ResponseEntity<Void> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    log.info("handleDataIntegrityViolationException {}", e.getMessage());
    return new ResponseEntity(new ErrorResponseBody().message(Messages.SLOT_ALREADY_REGISTERED.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @ExceptionHandler(HttpClientErrorException.class)
  @ResponseBody
  public ResponseEntity<Void> handleHttpClientErrorException(HttpClientErrorException e) {
    log.info("handleHttpClientErrorException {}", e.getMessage());
    return new ResponseEntity(new ErrorResponseBody().message(e.getStatusText()), e.getStatusCode());
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<Void> handleException(Exception e) {
    log.info("handleException {}", e.getMessage());
    return new ResponseEntity(new ErrorResponseBody().message(Messages.GENERIC_ERROR_MESSAGE.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
