package org.example.crmedu.application.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.example.crmedu.application.dto.error.SystemError;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class AdviceController {

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
