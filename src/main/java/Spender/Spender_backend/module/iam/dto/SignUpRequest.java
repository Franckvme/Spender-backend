package Spender.Spender_backend.module.iam.dto;

import Spender.Spender_backend.module.iam.entity.User;
import Spender.Spender_backend.module.iam.enums.ContactType;
import Spender.Spender_backend.module.iam.exception.InvalidSignUpRequestException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record SignUpRequest(
  @NotBlank() @Size(max = 120) String username,
  @NotBlank @Size(min = 12, max = 120) String password,
  @Size(max = 120) String email,
  @Size(max = 20)
  String phoneNumber

) {
  public User toUser(UUID id, String encryptedPassword) {
    return new User()
      .id(id)
      .username(username)
      .password(encryptedPassword)
      .email(email)
      .phoneNumber(phoneNumber);
  }
  public void validate() {
    boolean hasEmail = email != null && !email.trim().isEmpty();
    boolean hasPhone = phoneNumber != null && !phoneNumber.trim().isEmpty();

    if (hasEmail && !isValidEmail(email)) {
      throw InvalidSignUpRequestException.invalidEmailFormat(email);
    }

    if (hasPhone && !isValidPhoneNumber(phoneNumber)) {
      throw InvalidSignUpRequestException.invalidPhoneNumberFormat(phoneNumber);
    }

    if (!isValidUsername(username)) {
      throw InvalidSignUpRequestException.invalidUsernameFormat();
    }
  }

  public ContactType getContactType() {
    if (email != null && !email.trim().isEmpty()) {
      return ContactType.EMAIL;
    } else {
      return ContactType.PHONE;
    }
  }

  public String getContactValue() {
    return getContactType() == ContactType.EMAIL ? email : phoneNumber;
  }

  private boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    return email != null && email.matches(emailRegex);
  }

  private boolean isValidPhoneNumber(String phone) {
    String phoneRegex = "^(\\+[1-9]\\d{1,14}|0[1-9]\\d{8,9})$";
    return phone != null && phone.matches(phoneRegex);
  }

  private boolean isValidUsername(String username) {
    String usernameRegex = "^[a-zA-Z0-9_-]{3,50}$";
    return username != null && username.matches(usernameRegex);
  }
}
