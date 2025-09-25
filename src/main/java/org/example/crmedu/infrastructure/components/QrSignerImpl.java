package org.example.crmedu.infrastructure.components;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.example.crmedu.domain.components.QrSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QrSignerImpl implements QrSigner {

  @Value("${jwt.secret.access}")
  String secret;

  public String sign(String payload) {
    try {
      var keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
      var mac = Mac.getInstance("HmacSHA256");
      mac.init(keySpec);
      byte[] hash = mac.doFinal(payload.getBytes());
      return Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
      throw new RuntimeException("Error signing payload", e);
    }
  }

  public boolean verify(String payload, String signature) {
    return sign(payload).equals(signature);
  }
}
