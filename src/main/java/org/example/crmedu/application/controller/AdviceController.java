package org.example.crmedu.application.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.example.crmedu.application.dto.error.SystemError;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<SystemError> handleEntityNotFoundException(EntityNotFoundException e) {
    var error = getSystemError(e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<SystemError> handleEntityExistsException(EntityExistsException e) {
    var error = getSystemError(e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  private SystemError getSystemError(Exception e) {
    return new SystemError()
        .setMessage(e.getMessage())
        .setStackTrace(getStackTrace(e));
  }

  private String getStackTrace(Throwable ex) {
    Writer strWr = new StringWriter();
    PrintWriter wr = new PrintWriter(strWr);
    ex.printStackTrace(wr);
    return strWr.toString();
  }
}
