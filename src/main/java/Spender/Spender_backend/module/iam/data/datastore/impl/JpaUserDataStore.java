package Spender.Spender_backend.module.iam.data.datastore.impl;

import Spender.Spender_backend.module.iam.data.datastore.UserDataStore;
import Spender.Spender_backend.module.iam.data.repository.UserRepository;
import Spender.Spender_backend.module.iam.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JpaUserDataStore implements UserDataStore {

  private final UserRepository userRepository;

  @Override
  public User signUp(User user) {
    return userRepository.save(user);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsernameIgnoreCase(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }
    return userRepository.existsByEmailIgnoreCase(email);
  }

  @Override
  public boolean existsByPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
      return false;
    }
    return userRepository.existsByPhoneNumber(phoneNumber);
  }
}
