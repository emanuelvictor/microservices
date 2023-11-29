package com.emanuelvictor.api.functional.bertosimulators;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.InternetProtocol;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/datasets/truncate_all_tables.sql")
@EnableDiscoveryClient(autoRegister = false)
public abstract class AbstractIntegrationTests {

    public AbstractIntegrationTests() {
        final GenericContainer<?> postgres = new GenericContainer<>(DockerImageName.parse("postgres:13.2-alpine"))
                .withEnv("POSTGRES_USER", "bertosimulators")
                .withEnv("POSTGRES_PASSWORD", "bertosimulators")
                .withEnv("POSTGRES_DB", "bertosimulators");
        postgres.setPortBindings(Collections.singletonList(String.format("%d:%d/%s", 5433, 5432, InternetProtocol.TCP.toDockerNotation())));
        try {
            postgres.start();
        } catch (final Exception ignore) {
        }
    }
}
