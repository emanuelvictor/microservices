package com.emanuelvictor.api.functional.flowcreator;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Alternative;
import com.emanuelvictor.api.functional.flowcreator.domain.repositories.AlternativeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
@EnableAsync
//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
public class FlowCreatorApplication extends SpringBootServletInitializer {

    @Autowired
    AlternativeRepository alternativeRepository;

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(FlowCreatorApplication.class);

    /**
     * @param args String[]
     */
    public static void main(final String[] args) {
        SpringApplication.run(FlowCreatorApplication.class, args);
    }

    /**
     * @param application SpringApplicationBuilder
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(FlowCreatorApplication.class);
    }

    /**
     * @return ApplicationListener<ApplicationReadyEvent>
     */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> getApplicationReadyEvent() {
        return applicationReadyEvent -> {
            LOGGER.info("--------------------------------------------------");

            final List<String> profiles = Arrays.asList(applicationReadyEvent.getApplicationContext().getEnvironment().getActiveProfiles());

            if (profiles.isEmpty()) {
                LOGGER.info("Sistema iniciado com o perfil de configuração: dev");
            }

            profiles.forEach(profile ->
                    LOGGER.info("Sistema iniciado com o perfil de configuração: {}", profile)
            );
            LOGGER.info("--------------------------------------------------");


            choice(1L);
        };
    }

    public void choice(final Long alternativeId) {
        final List<Alternative> alternatives = alternativeRepository.findByPreviousId(alternativeId);
        if(alternatives.size() == 0)
            choice(1L);
        final HashMap<Integer, Long> alternativesMap = new HashMap<>();
        for (int i = 0; i < alternatives.size(); i++) {
            System.out.println(i + 1 + " - " + alternatives.get(i).getOption().getName());
            alternativesMap.put(i + 1, alternatives.get(i).getId());
        }
        final Scanner myObj = new Scanner(System.in);
        final Integer choice = Integer.valueOf(myObj.nextLine());
        choice(alternativesMap.get(choice));
    }
}
