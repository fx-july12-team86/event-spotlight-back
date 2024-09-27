package org.example.eventspotlightback.config;

import org.testcontainers.containers.MySQLContainer;

import java.time.Duration;

public class CustomMySqlContainer extends MySQLContainer<CustomMySqlContainer> {
    private static final String DB_IMAGE = "mysql:8.0.33";

    private static CustomMySqlContainer mySqlContainer;

    private CustomMySqlContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomMySqlContainer getInstance() {
        if (mySqlContainer == null) {
            mySqlContainer = new CustomMySqlContainer().withStartupTimeout(Duration.ofMinutes(5));
        }
        return mySqlContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", mySqlContainer.getJdbcUrl());
        System.setProperty("TEST_DB_USER", mySqlContainer.getUsername());
        System.setProperty("TEST_DB_PASSWORD", mySqlContainer.getPassword());
    }

    @Override
    public void stop() {
    }
}