package Spender.Spender_backend.shared.exception.database;

import Spender.Spender_backend.shared.exception.base.SpenderBusinessException;
import org.springframework.http.HttpStatus;

public class DatabaseNonTransientException extends SpenderBusinessException {
    public DatabaseNonTransientException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST);
    }
}
