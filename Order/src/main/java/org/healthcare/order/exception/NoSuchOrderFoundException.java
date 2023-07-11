package org.healthcare.order.exception;

public class NoSuchOrderFoundException extends RuntimeException {
  public NoSuchOrderFoundException(String errorMessage) {
    super(errorMessage);
  }
}
