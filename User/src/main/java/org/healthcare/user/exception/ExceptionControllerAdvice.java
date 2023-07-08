package org.healthcare.user.exception;

import org.healthcare.user.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(DuplicateUserNameException.class)
  public ResponseEntity<?> handleDuplicateUserNameException(DuplicateUserNameException exception) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.OK.value(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.OK);
  }

  @ExceptionHandler(NoSuchUserFoundException.class)
  public ResponseEntity<?> handleNoSuchUserFoundException(NoSuchUserFoundException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
