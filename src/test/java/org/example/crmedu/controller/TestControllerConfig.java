package org.example.crmedu.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestControllerConfig {

  @Bean
  public MockCreator mockCreator() {
    return new MockCreator();
  }
}
