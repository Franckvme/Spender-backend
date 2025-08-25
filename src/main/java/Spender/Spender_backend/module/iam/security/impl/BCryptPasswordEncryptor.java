package Spender.Spender_backend.module.iam.security.impl;

import Spender.Spender_backend.module.iam.security.PasswordEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncryptor implements PasswordEncryptor {

  private final PasswordEncoder passwordEncoder;

  public BCryptPasswordEncryptor(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encrypt(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Override
  public boolean matches(String rawPassword, String encryptedPassword) {
    return passwordEncoder.matches(rawPassword, encryptedPassword);
  }
}
