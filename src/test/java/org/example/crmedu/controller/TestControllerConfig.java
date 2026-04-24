package org.example.crmedu.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class for testing controllers. It provides a bean for {@link MockCreator}, which is used to create test entities via API calls.
 */
@Configuration
@Profile("test")
public class TestControllerConfig {

  @Bean
  public MockCreator mockCreator() {
    return new MockCreator();
  }
}
