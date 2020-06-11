package com.springtest.spring.exception;

public class HelloException extends RuntimeException {
  private static final long serialVersionUID = 4115322292912226894L;

  public HelloException(String msg) {
    super(msg);
  }
}
