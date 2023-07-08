package org.healthcare.order.exception;

public class InvalidUserIdException extends RuntimeException{

  public InvalidUserIdException(String errorMessage) {
    super(errorMessage);
  }
}
