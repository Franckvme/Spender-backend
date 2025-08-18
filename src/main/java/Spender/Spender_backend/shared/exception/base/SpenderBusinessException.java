package Spender.Spender_backend.shared.exception.base;

import org.springframework.http.HttpStatus;

public class SpenderBusinessException extends SpenderException {

    public SpenderBusinessException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public SpenderBusinessException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }
}
