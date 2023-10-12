package com.emanuelvictor.api.functional.accessmanager.application.beans;

import com.emanuelvictor.api.functional.accessmanager.domain.services.ServiceToRemoveLowerPermissions;
import com.emanuelvictor.api.functional.accessmanager.domain.services.ServiceToRemoveLowerPermissionsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceToInjectionOfDependencies {

    @Bean
    ServiceToRemoveLowerPermissions serviceToRemoveLowerPermissions() {
        return new ServiceToRemoveLowerPermissionsImpl();
    }
}
