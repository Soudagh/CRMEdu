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

/**
 * Global exception handler for handling application-wide exceptions. Provides centralized exception processing and returns appropriate HTTP responses.
 */
@ControllerAdvice
public class AdviceController {

  /**
   * Handles {@link EntityNotFoundException} and returns a 404 Not Found response.
   *
   * @param e the thrown exception
   * @return a {@link ResponseEntity} containing a {@link SystemError} object with error details
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<SystemError> handleEntityNotFoundException(EntityNotFoundException e) {
    var error = getSystemError(e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  /**
   * Handles {@link EntityExistsException} and returns a 400 Bad Request response.
   *
   * @param e the thrown exception
   * @return a {@link ResponseEntity} containing a {@link SystemError} object with error details
   */
  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<SystemError> handleEntityExistsException(EntityExistsException e) {
    var error = getSystemError(e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  /**
   * Creates a {@link SystemError} object containing the exception message and stack trace.
   *
   * @param e the exception to be processed
   * @return a {@link SystemError} object with error details
   */
  private SystemError getSystemError(Exception e) {
    return new SystemError()
        .setMessage(e.getMessage())
        .setStackTrace(getStackTrace(e));
  }

  /**
   * Converts the stack trace of an exception into a string.
   *
   * @param ex the exception to process
   * @return the stack trace as a string
   */
  private String getStackTrace(Throwable ex) {
    Writer strWr = new StringWriter();
    PrintWriter wr = new PrintWriter(strWr);
    ex.printStackTrace(wr);
    return strWr.toString();
  }
}