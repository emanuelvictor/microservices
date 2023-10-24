package com.emanuelvictor.api.functional.accessmanager.domain.repositories;

import com.emanuelvictor.api.functional.accessmanager.AbstractIntegrationTests;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
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

    private Permission rootPermission;

    @BeforeEach
    void setUp() {
        rootPermission = permissionRepository.findById(1L).orElseThrow();
    }

    @Test
    public void mustSaveGroupPermission() {
        final var group = Group.builder().name("Access Group Name").build();
        groupRepository.save(group);
        var groupPermissionToSave = GroupPermission.builder()
                .permission(rootPermission)
                .group(group)
                .build();

        final GroupPermission groupPermissionSaved = groupPermissionRepository.save(groupPermissionToSave);

        Assertions.assertThat(groupPermissionSaved).isEqualTo(groupPermissionToSave);
    }

    @Test
    public void saveTwoGroupPermissionsAndMustReturnOnlyGroupPermissionsFromFistGroup() {
        var groupOne = Group.builder().name("groupOne").build();
        var groupTwo = Group.builder().name("groupTwo").build();
        groupRepository.saveAll(Arrays.asList(groupOne, groupTwo));
        var permissionOne = Permission.builder()
                .upperPermission(rootPermission)
                .name("permissionOne")
                .authority("authorityPermissionOne")
                .build();
        var permissionTwo = Permission.builder()
                .upperPermission(rootPermission)
                .name("permissionTwo")
                .authority("authorityPermissionTwo")
                .build();
        permissionRepository.saveAll(Arrays.asList(permissionOne, permissionTwo));
        var groupPermissionToGroupOne = GroupPermission.builder()
                .permission(permissionOne)
                .group(groupOne)
                .build();
        var groupPermissionToGroupTwo = GroupPermission.builder()
                .permission(permissionTwo)
                .group(groupTwo)
                .build();
        groupPermissionRepository.saveAll(Arrays.asList(groupPermissionToGroupOne, groupPermissionToGroupTwo));

        var groupPermissions = groupPermissionRepository.findByGroupId(groupOne.getId(), Pageable.unpaged());

        Assertions.assertThat(groupPermissions).extracting(GroupPermission::getGroup).containsExactly(groupOne);
    }

    @Test
    public void saveTwoGroupPermissionsAndMustReturnOnlyGroupPermissionsSecondFistGroup() {
        var groupOne = Group.builder().name("groupOne").build();
        var groupTwo = Group.builder().name("groupTwo").build();
        groupRepository.saveAll(Arrays.asList(groupOne, groupTwo));
        var permissionOne = Permission.builder()
                .upperPermission(rootPermission)
                .name("permissionOne")
                .authority("authorityPermissionOne")
                .build();
        var permissionTwo = Permission.builder()
                .upperPermission(rootPermission)
                .name("permissionTwo")
                .authority("authorityPermissionTwo")
                .build();
        permissionRepository.saveAll(Arrays.asList(permissionOne, permissionTwo));
        var groupPermissionToGroupOne = GroupPermission.builder()
                .permission(permissionOne)
                .group(groupOne)
                .build();
        var groupPermissionToGroupTwo = GroupPermission.builder()
                .permission(permissionTwo)
                .group(groupTwo)
                .build();
        groupPermissionRepository.saveAll(Arrays.asList(groupPermissionToGroupOne, groupPermissionToGroupTwo));

        var groupPermissions = groupPermissionRepository.findByGroupId(groupTwo.getId(), Pageable.unpaged());

        Assertions.assertThat(groupPermissions).extracting(GroupPermission::getGroup).containsExactly(groupTwo);
    }

}
