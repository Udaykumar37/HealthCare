package com.healthcare.pharmacy.exception;

public class NoSuchMedicineException extends RuntimeException {
  public NoSuchMedicineException(String exceptionMessage) {
    super(exceptionMessage);
  }
}
