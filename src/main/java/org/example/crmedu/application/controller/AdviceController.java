package org.example.crmedu.application.controller;

import jakarta.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import org.example.crmedu.application.dto.error.SystemError;
import org.example.crmedu.application.dto.error.ValidationErrorResponse;
import org.example.crmedu.application.dto.error.Violation;
import org.example.crmedu.domain.exception.EntityExistsException;
import org.example.crmedu.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
   * Handles a {@link ConstraintViolationException} and returns an HTTP 400 Bad Request response.
   *
   * @param e the exception containing validation errors
   * @return a {@link ResponseEntity} containing a {@link ValidationErrorResponse} with error details
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ValidationErrorResponse> handleConstraintValidationException(ConstraintViolationException e) {
    final List<Violation> violations = e.getConstraintViolations().stream()
        .map(violation -> new Violation(
                violation.getPropertyPath().toString(),
                violation.getMessage()
            )
        ).toList();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse(violations));
  }

  /**
   * Handles a {@link MethodArgumentNotValidException} and returns an HTTP 400 Bad Request response.
   * <p>
   * This exception is thrown when validation on an argument annotated with {@code @Valid} fails. The method extracts field validation errors and returns them
   * in a structured response.
   * </p>
   *
   * @param e the exception containing validation errors
   * @return a {@link ResponseEntity} containing a {@link ValidationErrorResponse} with the list of validation violations
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
        .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
        .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse(violations));
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