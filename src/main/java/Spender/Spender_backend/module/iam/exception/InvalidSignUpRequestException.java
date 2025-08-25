package Spender.Spender_backend.module.iam.exception;

import Spender.Spender_backend.shared.exception.base.SpenderBusinessException;
import org.springframework.http.HttpStatus;

public class InvalidSignUpRequestException extends SpenderBusinessException {

  public InvalidSignUpRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public static InvalidSignUpRequestException invalidEmailFormat(String email) {
        return new InvalidSignUpRequestException("Invalid email format"+ email);
    }

    public static InvalidSignUpRequestException invalidPhoneNumberFormat(String phoneNumber) {
        return new InvalidSignUpRequestException("Invalid phone number format: " + phoneNumber);
    }

    public static InvalidSignUpRequestException invalidUsernameFormat() {
        return new InvalidSignUpRequestException("The username must contain between 3 and 50 characters.");
    }
}
