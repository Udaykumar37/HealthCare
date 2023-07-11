package org.healthcare.user.exception;

public class DuplicateUserNameException extends RuntimeException{
  public DuplicateUserNameException(String errorMessage) {
    super(errorMessage);
  }
}
