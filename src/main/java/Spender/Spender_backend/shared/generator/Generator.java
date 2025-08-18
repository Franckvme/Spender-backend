package Spender.Spender_backend.shared.generator;

public interface Generator<T> {

  /**
   * Generate a new identifier of type T.
   *
   * @return the generated identifier
   */
  T next();
}
