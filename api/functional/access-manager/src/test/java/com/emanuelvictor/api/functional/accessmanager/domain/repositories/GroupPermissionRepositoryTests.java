package com.emanuelvictor.api.functional.accessmanager.domain.repositories;

import com.emanuelvictor.api.functional.accessmanager.AbstractIntegrationTests;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.InternetProtocol;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.github.dockerjava.core.dockerfile.Dockerfile;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

public class GroupPermissionRepositoryTests extends AbstractIntegrationTests {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private GroupPermissionRepository groupPermissionRepository;

    private final Group group = new Group();
    private Permission rootPermission;

    @BeforeEach
    void setUp() {
        group.setName("Access Group Name");
        groupRepository.save(group);
        rootPermission = permissionRepository.findById(1L).orElseThrow();
    }

    @Test
    public void mustListGroupsPermissionsByGroupId() {
        System.out.println(" ------ ");
        final GroupPermission groupPermission = new GroupPermission();
        groupPermission.setPermission(rootPermission);
        groupPermission.setGroup(group);
        groupPermissionRepository.save(groupPermission);

        final List<GroupPermission> groupsPermissionsSaved = groupPermissionRepository.listByGroupId(group.getId());

        System.out.println(groupsPermissionsSaved.size());
    }

}
