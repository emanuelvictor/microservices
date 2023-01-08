package com.emanuelvictor.api.functional.flowcreator;

import com.emanuelvictor.api.functional.flowcreator.domain.entity.Choice;
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.AbstractAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.IntermediaryAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.entity.alternative.RootAlternative;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.AlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.IntermediaryAlternativeRepository;
import com.emanuelvictor.api.functional.flowcreator.domain.ports.ChoiceRepository;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.helprs.PopulateHelper;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@EnableAsync
//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
public class FlowCreatorApplication extends SpringBootServletInitializer {

    /**
     *
     */
    @Autowired
    PopulateHelper populateHelper;

    /**
     *
     */
    @Autowired
    ChoiceRepository choiceRepository;

    /**
     *
     */
    @Autowired
    AlternativeRepository alternativeRepository;

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

           start();
        };
    }

    public void start(){
        final RootAlternative rootAlternative = alternativeRepository.findAllRootAlternatives().findFirst().orElseThrow();
        choice(rootAlternative.getId());
    }

    public void choice(final Long alternativeId) {
        final AbstractAlternative abstractAlternative = alternativeRepository.findById(alternativeId).orElseThrow();
        final List<IntermediaryAlternative> intermediaryAlternatives = alternativeRepository.findByPreviousId(abstractAlternative.getId()).collect(Collectors.toList());
        if (intermediaryAlternatives.size() == 0) {
            final Choice choice = new Choice(abstractAlternative);
            choiceRepository.save(choice);
            final Map<String, List<Choice>> choicesMap = choiceRepository.findAll().collect(Collectors.groupingBy(Choice::getPath));
            choicesMap.forEach((k, v) -> System.out.println(k + " = " + v.size()));
            System.out.println("------------------------------------------------");
            start();
        }
        System.out.println(abstractAlternative.getMessageToNext());
        final HashMap<Integer, Long> alternativesMap = new HashMap<>();
        for (int i = 0; i < intermediaryAlternatives.size(); i++) {
            System.out.println(i + 1 + " - " + intermediaryAlternatives.get(i).getOption().getName());
            alternativesMap.put(i + 1, intermediaryAlternatives.get(i).getId());
        }
        System.out.println("0 - exit");
        final Scanner myObj = new Scanner(System.in);
        final int choice = Integer.parseInt(myObj.nextLine());
        if (choice == 0)
            System.exit(-1);
        choice(alternativesMap.get(choice));
    }

}
