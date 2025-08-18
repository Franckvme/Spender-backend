package Spender.Spender_backend.shared.exception.base;

import org.springframework.http.HttpStatus;

public class SpenderTechnicalException extends SpenderException {

    public SpenderTechnicalException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public SpenderTechnicalException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause, httpStatus);
    }
}
