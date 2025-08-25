package Spender.Spender_backend.module.iam.data.datastore;

import Spender.Spender_backend.module.iam.entity.User;
import Spender.Spender_backend.module.iam.enums.ContactType;
import Spender.Spender_backend.module.iam.exception.UserAlreadyExistsException;
import Spender.Spender_backend.shared.annotation.DatabaseRetryable;

@DatabaseRetryable
public interface UserDataStore {

  User signUp(User user);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByPhoneNumber(String phoneNumber);

  default void requireNotExistsByUsername(String username) {
    if (existsByUsername(username)) {
       throw UserAlreadyExistsException.usernameAlreadyUsed();
    }
  }

  default void requireNotExistsByEmail(String email) {
    if (existsByEmail(email)) {
      throw UserAlreadyExistsException.emailAlreadyUsed(email);
    }
  }

  default void requireNotExistsByPhoneNumber(String phoneNumber) {
    if (existsByPhoneNumber(phoneNumber)) {
      throw UserAlreadyExistsException.phoneNumberAlreadyUsed(phoneNumber);
    }
  }

  default void requireNotExistsByContact(String contactValue, ContactType contactType) {
    switch (contactType) {
      case EMAIL -> requireNotExistsByEmail(contactValue);
      case PHONE -> requireNotExistsByPhoneNumber(contactValue);
    }
  }
}
