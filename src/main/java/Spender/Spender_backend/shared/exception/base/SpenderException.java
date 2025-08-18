package Spender.Spender_backend.shared.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SpenderException extends RuntimeException {

    private final HttpStatus httpStatus;

    public SpenderException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public SpenderException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
