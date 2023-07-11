package com.healthcare.pharmacy.exception;

import com.healthcare.pharmacy.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(NoSuchMedicineException.class)
  public ResponseEntity<?> handleMedicineNotFoundException(NoSuchMedicineException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
