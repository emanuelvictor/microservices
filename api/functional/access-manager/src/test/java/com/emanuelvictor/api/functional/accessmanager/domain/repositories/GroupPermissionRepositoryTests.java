package com.emanuelvictor.api.functional.accessmanager.domain.repositories;

import com.emanuelvictor.api.functional.accessmanager.AbstractIntegrationTests;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import com.emanuelvictor.api.functional.accessmanager.domain.entity.GroupBuilder;
import com.emanuelvictor.api.functional.accessmanager.domain.entity.PermissionBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

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
        rootPermission = new PermissionBuilder().authority("root").name("Root").build();
        permissionRepository.save(rootPermission);
    }

    @Test
    public void mustSaveGroupPermission() {
        final var group = new GroupBuilder().name("Access Group Name").build();
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
        var groupOne = new GroupBuilder().name("groupOne").build();
        var groupTwo = new GroupBuilder().name("groupTwo").build();
        groupRepository.saveAll(Arrays.asList(groupOne, groupTwo));
        var permissionOne = new PermissionBuilder()
                .upperPermission(rootPermission)
                .name("permissionOne")
                .authority("authorityPermissionOne")
                .build();
        var permissionTwo = new PermissionBuilder()
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
        var groupOne = new GroupBuilder().name("groupOne").build();
        var groupTwo = new GroupBuilder().name("groupTwo").build();
        groupRepository.saveAll(Arrays.asList(groupOne, groupTwo));
        var permissionOne = new PermissionBuilder()
                .upperPermission(rootPermission)
                .name("permissionOne")
                .authority("authorityPermissionOne")
                .build();
        var permissionTwo = new PermissionBuilder()
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
