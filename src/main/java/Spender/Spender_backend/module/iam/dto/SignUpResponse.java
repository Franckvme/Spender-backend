package Spender.Spender_backend.module.iam.dto;

import Spender.Spender_backend.module.iam.entity.User;
import Spender.Spender_backend.module.iam.enums.ContactType;

import java.util.UUID;

public record SignUpResponse(
  UUID id,
  String username,
  String email,
  String phoneNumber,
  ContactType contactType
) {
  public static SignUpResponse fromUser(User user) {
    ContactType type = determineContactType(user);
    return new SignUpResponse(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPhoneNumber(),
      type
    );
  }

  private static ContactType determineContactType(User user) {
    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
      return ContactType.EMAIL;
    } else {
      return ContactType.PHONE;
    }
  }
}
