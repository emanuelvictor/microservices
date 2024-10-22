package com.emanuelvictor.api.functional.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TestApplication {

    /**
     *
     * @param args String[]
     */
    public static void main(final String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
