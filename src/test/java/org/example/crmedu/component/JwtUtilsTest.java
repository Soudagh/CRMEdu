package org.example.crmedu.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.Claims;
import java.lang.reflect.Modifier;
import org.example.crmedu.BaseUnitTest;
import org.example.crmedu.domain.enums.Role;
import org.example.crmedu.infrastructure.auth.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest extends BaseUnitTest {

  @Test
  void generate_shouldReturnJwtAuthenticationWithCorrectValues() {
    var claims = mock(Claims.class);
    when(claims.getSubject()).thenReturn("user@mail.com");
    when(claims.get("role", String.class)).thenReturn("SUPERUSER");
    var jwtAuth = JwtUtils.generate(claims);
    assertNotNull(jwtAuth);
    assertEquals("user@mail.com", jwtAuth.getEmail());
    assertEquals(Role.SUPERUSER, jwtAuth.getRole());
  }

  @Test
  void getRole_shouldReturnCorrectRole() {
    var claims = mock(Claims.class);
    when(claims.get("role", String.class)).thenReturn("USER");
    var role = JwtUtils.generate(claims).getRole();
    assertEquals(Role.USER, role);
  }

  @Test
  void getRole_shouldThrowExceptionForInvalidRole() {
    var claims = mock(Claims.class);
    when(claims.get("role", String.class)).thenReturn("INVALID_ROLE");
    assertThrows(IllegalArgumentException.class, () -> JwtUtils.generate(claims));
  }

  @Test
  void jwtUtils_shouldBeFinal() {
    int modifiers = JwtUtils.class.getModifiers();
    assertTrue(Modifier.isFinal(modifiers));
  }
}
