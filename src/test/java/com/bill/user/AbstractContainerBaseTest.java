package com.bill.user;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles({ "test" })
@TestPropertySource(locations = { "classpath:application-test.properties", "classpath:error-messages.properties" })
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration",
		"spring.security.filter.disabled=true" })
public abstract class AbstractContainerBaseTest {

	@Autowired
	protected TestRestTemplate restTemplate;

	@Container
	@ServiceConnection
	static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:13-alpine"))
			.withDatabaseName("bill")
			.withUsername("bill")
			.withPassword("password");

	@BeforeAll
	static void checkContainerIsRunning() {
		if (!postgres.isRunning()) {
			throw new IllegalStateException("PostgreSQL container failed to start!");
		}

		System.out.println("\n=== Test Container Info ===");
		System.out.println("JDBC URL: " + postgres.getJdbcUrl());
		System.out.println("Database: " + postgres.getDatabaseName());
		System.out.println("Username: " + postgres.getUsername());
		System.out.println("Password: " + postgres.getPassword());
		System.out.println("==========================\n");
	}

}