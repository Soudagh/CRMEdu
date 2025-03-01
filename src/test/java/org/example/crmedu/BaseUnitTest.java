package org.example.crmedu;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import lombok.SneakyThrows;
import org.instancio.Instancio;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

/**
 * Base class for unit tests. Provides utility methods for JSON processing, file reading, and mock object creation using Instancio.
 */
public abstract class BaseUnitTest {

  protected static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * Reads a JSON file from the classpath and deserializes it into an object.
   *
   * @param filePath the path to the JSON file
   * @param clazz the target class type
   * @param <T> the type of the returned object
   * @return deserialized object of type {@code T}
   */
  @SneakyThrows
  protected <T> T getObjectFromJson(String filePath, Class<T> clazz) {
    File file = ResourceUtils.getFile(String.format("classpath:%s", filePath));
    return objectMapper.readValue(file, clazz);
  }

  /**
   * Reads the contents of a file as a String.
   *
   * @param path the file path
   * @return file content as a string
   */
  @SneakyThrows
  protected String readFileAsString(String path) {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(path);
    return asString(resource);
  }

  /**
   * Creates a mock object of the specified class using Instancio.
   *
   * @param mockClass the class to mock
   * @param <T> the type of the mock object
   * @return a new mock instance of type {@code T}
   */
  protected <T> T getMockObject(Class<T> mockClass) {
    return Instancio
        .of(mockClass)
        .lenient()
        .create();
  }

  @SneakyThrows
  protected void setPrivateField(Object target, String fieldName, Object value) {
    Field field = target.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(target, value);
  }

  /**
   * Converts a {@link Resource} to a String.
   *
   * @param resource the resource to read
   * @return the resource content as a string
   * @throws IOException if an I/O error occurs
   */
  private String asString(Resource resource) throws IOException {
    try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    }
  }
}
