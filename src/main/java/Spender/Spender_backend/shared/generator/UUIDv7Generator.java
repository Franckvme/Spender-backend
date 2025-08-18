package Spender.Spender_backend.shared.generator;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDv7Generator implements IdGenerator {

  @Override
  public UUID next() {
    return UuidCreator.getTimeOrderedEpoch();
  }
}
