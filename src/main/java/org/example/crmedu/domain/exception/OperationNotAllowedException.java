package org.example.crmedu.domain.exception;

public class OperationNotAllowedException extends RuntimeException {

  public OperationNotAllowedException(String message) {
    super(message);
  }
}