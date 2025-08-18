package Spender.Spender_backend;

import org.springframework.boot.SpringApplication;

public class TestSpenderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpenderBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
