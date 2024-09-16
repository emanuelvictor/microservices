package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.AbstractIntegrationTests;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entity.GroupBuilder;
import com.emanuelvictor.api.functional.accessmanager.domain.entity.PermissionBuilder;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupServiceTest extends AbstractIntegrationTests {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private GroupPermissionRepository accessGroupPermissionRepository;
    @Autowired
    private LinkPermissionToGroupService linkPermissionToGroupService;

    @Test
    void mustDeleteAccessGroupById() {
        final Group group = new GroupBuilder().build();
        groupRepository.save(group);
        final var permission = new PermissionBuilder().build();
        permissionRepository.save(permission);
        linkPermissionToGroupService.link(group.getId(), permission.getAuthority());

        groupService.delete(group.getId());

        assertThat(groupRepository.findById(group.getId())).isEmpty();
        assertThat(accessGroupPermissionRepository.listByFilters(group.getId(), permission.getAuthority(), null))
                .isEmpty();
        assertThat(permissionRepository.findByAuthority(permission.getAuthority()).orElseThrow().getAuthority())
                .isEqualTo(permission.getAuthority());
    }
}