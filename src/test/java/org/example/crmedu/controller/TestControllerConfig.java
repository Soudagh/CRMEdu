package org.example.crmedu.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for testing controllers. It provides a bean for {@link MockCreator}, which is used to create test entities via API calls.
 */
@Configuration
public class TestControllerConfig {

  @Bean
  public MockCreator mockCreator() {
    return new MockCreator();
  }
}
