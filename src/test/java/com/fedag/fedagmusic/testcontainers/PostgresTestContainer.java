package com.fedag.fedagmusic.testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    public static final String IMAGE_VERSION = "postgres:latest";
    public static final String DATABASE_NAME = "test";
    public static PostgreSQLContainer container;

    public PostgresTestContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLContainer getInstance() {
        if (container == null) {
            container = new PostgresTestContainer().withDatabaseName(DATABASE_NAME);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("r2dbc:postgresql://localhost:5432/fm-postgres", container.getJdbcUrl());
        System.setProperty("postgres", container.getUsername());
        System.setProperty("postgres", container.getPassword());
    }

    @Override
    public void stop() {
    }
}
