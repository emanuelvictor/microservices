package com.emanuelvictor.api.functional.accessmanager.infrastructure.jpa;


import org.hibernate.dialect.PostgreSQL9Dialect;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 10/09/2020
 */
public class CustomPostgresDialect extends PostgreSQL9Dialect {
    /**
     *
     */
    public CustomPostgresDialect() {
        super.registerFunction("FILTER", new PostgresFilterFunction());
    }
}
