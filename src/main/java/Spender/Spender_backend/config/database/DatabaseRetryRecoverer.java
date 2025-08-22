package Spender.Spender_backend.config.database;

import Spender.Spender_backend.shared.exception.base.SpenderException;
import Spender.Spender_backend.shared.exception.database.DatabaseException;
import Spender.Spender_backend.shared.exception.database.DatabaseNonTransientException;
import lombok.SneakyThrows;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;

public class DatabaseRetryRecoverer implements MethodInvocationRecoverer<Object> {

  @SneakyThrows
  @Override
  public Object recover(Object[] args, Throwable cause) {

    if (cause instanceof SpenderException) {
      throw (SpenderException) cause;
    }

    if (cause instanceof NonTransientDataAccessException) {
      throw new DatabaseNonTransientException(cause.getMessage(), cause);
    }

    throw new DatabaseException(cause.getMessage(), cause);
  }
}
