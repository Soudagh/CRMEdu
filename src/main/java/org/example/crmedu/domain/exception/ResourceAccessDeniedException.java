package org.example.crmedu.domain.exception;

public class ResourceAccessDeniedException extends RuntimeException {

  public ResourceAccessDeniedException(String message) {
    super(message);
  }
}