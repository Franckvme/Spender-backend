package Spender.Spender_backend.module.iam.exception;

import Spender.Spender_backend.shared.exception.base.SpenderBusinessException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends SpenderBusinessException {

    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public static UserAlreadyExistsException usernameAlreadyUsed() {
        return new UserAlreadyExistsException("Username already exists");
    }

    public static UserAlreadyExistsException emailAlreadyUsed(String email) {
        return new UserAlreadyExistsException("Email already used: " + email);
    }

    public static UserAlreadyExistsException phoneNumberAlreadyUsed(String phoneNumber) {
        return new UserAlreadyExistsException("Phone number already used: " + phoneNumber);
    }
}
