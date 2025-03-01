package org.example.crmedu.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.application.event.RegistrationListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegistrationListenerTest extends BaseUnitTest {

  @InjectMocks
  private RegistrationListener registrationListener;

  @Test
  void onApplicationEvent_shouldThrowException_ifPassNull() {
    assertThrows(NullPointerException.class, () -> registrationListener.onApplicationEvent(null));
  }
}
