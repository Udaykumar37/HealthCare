package org.healthcare.user.exception;

public class NoSuchUserFoundException extends RuntimeException {
  public NoSuchUserFoundException(String errorMessage) {
    super(errorMessage);
  }
}
