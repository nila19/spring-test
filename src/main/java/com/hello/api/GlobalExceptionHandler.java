package com.hello.api;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hello.exception.HelloException;

@Generated // exclude from Jacoco test coverage
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @Hidden
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HelloException.class)
  public void handleHelloException(final HelloException e) {
    log.error(e.toString());
  }

  @Hidden
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public void handleIllegalArgumentException(final IllegalArgumentException e) {
    log.error(e.toString());
  }

  @Hidden
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public void handleException(final Exception e) {
    log.error(e.getMessage());
  }
}
