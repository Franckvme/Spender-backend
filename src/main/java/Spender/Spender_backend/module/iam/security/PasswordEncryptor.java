package Spender.Spender_backend.module.iam.security;

public interface PasswordEncryptor {
  String encrypt(String rawPassword);

  boolean matches(String rawPassword, String encryptedPassword);
}

