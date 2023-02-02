package com.emanuelvictor.api.functional.flowcreator;

import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 */
@SpringBootApplication
public class FlowCreatorApplication extends SpringBootServletInitializer {

    /**
     *
     */
    @Autowired
    PopulateHelper populateHelper;

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

            populateHelper.eraseData();
            populateHelper.populateData();

            populateHelper.startProgram();
        };
    }

}
