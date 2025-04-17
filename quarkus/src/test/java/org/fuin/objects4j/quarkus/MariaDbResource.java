package org.fuin.objects4j.quarkus;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MariaDBContainer;

import java.util.Collections;
import java.util.Map;

public class MariaDbResource implements QuarkusTestResourceLifecycleManager {

	static MariaDBContainer<?> db = new MariaDBContainer<>("mariadb:11")
			.withDatabaseName("testdb")
			.withUsername("mary")
			.withPassword("abc");

	@Override
	public Map<String, String> start() {
		db.start();
		return Collections.singletonMap("quarkus.datasource.jdbc.url", db.getJdbcUrl());
	}

	@Override
	public void stop() {
		db.stop();
	}
	
}
