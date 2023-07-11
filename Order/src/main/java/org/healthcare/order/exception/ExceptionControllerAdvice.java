package org.healthcare.order.exception;

import org.healthcare.order.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(InvalidUserIdException.class)
  public ResponseEntity<?> handleInvalidUserIdException(InvalidUserIdException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidMedicineIdException.class)
  public ResponseEntity<?> handleInvalidMedicineIdException(InvalidMedicineIdException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoSuchOrderFoundException.class)
  public ResponseEntity<?> handleNoSuchOrderFoundException(NoSuchOrderFoundException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
