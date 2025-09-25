package org.example.crmedu.domain.components;

public interface QrSigner {

  String sign(String payload);

  boolean verify(String payload, String signature);
}
