package Spender.Spender_backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfig {

  public static final String TIME_ZONE = "Africa/Douala";
  public static final ZoneId ZONE_ID = ZoneId.of(TIME_ZONE);

  @Bean
  public Clock clock() {
    return Clock.system(ZONE_ID);
  }

  @PostConstruct
  public void init() {
    System.setProperty("iam.timezone", TIME_ZONE);
  }
}
