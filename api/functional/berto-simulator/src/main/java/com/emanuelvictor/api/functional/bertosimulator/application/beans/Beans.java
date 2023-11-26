package com.emanuelvictor.api.functional.bertosimulator.application.beans;

import com.emanuelvictor.api.functional.bertosimulator.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.bertosimulator.domain.repositories.GroupRepository;
import com.emanuelvictor.api.functional.bertosimulator.domain.repositories.PermissionRepository;
import com.emanuelvictor.api.functional.bertosimulator.domain.services.LinkPermissionToGroupService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    LinkPermissionToGroupService linkPermissionToGroupService(final GroupRepository groupRepository,
                                                              final PermissionRepository permissionRepository,
                                                              final GroupPermissionRepository groupPermissionRepository) {
        return new LinkPermissionToGroupService(groupRepository, permissionRepository, groupPermissionRepository);
    }
}
