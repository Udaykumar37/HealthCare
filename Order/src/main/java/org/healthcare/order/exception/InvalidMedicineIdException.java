package org.healthcare.order.exception;

public class InvalidMedicineIdException extends RuntimeException{
  public InvalidMedicineIdException(String errorMessage) {
    super(errorMessage);
  }
}
