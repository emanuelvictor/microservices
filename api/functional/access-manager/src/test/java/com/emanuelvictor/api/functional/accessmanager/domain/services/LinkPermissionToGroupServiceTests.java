package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.AbstractIntegrationTests;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.generic.PersistentEntity;
import com.emanuelvictor.api.functional.accessmanager.domain.entity.GroupBuilder;
import com.emanuelvictor.api.functional.accessmanager.domain.entity.PermissionBuilder;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.PermissionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class LinkPermissionToGroupServiceTests extends AbstractIntegrationTests {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private GroupPermissionRepository groupPermissionRepository;
    @Autowired
    private LinkPermissionToGroupService linkPermissionToGroupService;

    private final Group group = new GroupBuilder().build();
    private final Permission rootPermission = new PermissionBuilder().authority("1").build();

    @BeforeEach
    void setUp() {
        insertTreeOfPermissions();
        groupRepository.save(group);
    }

    @Test
    void mustLinkPermissionToGroup() {

        linkPermissionToGroupService.link(group, rootPermission);

        final var permissionsOfGroup = groupPermissionRepository.findByGroupId(group.getId(), null);
        Assertions.assertThat(permissionsOfGroup.getSize()).isEqualTo(1);
        Assertions.assertThat(permissionsOfGroup.getContent())
                .extracting(GroupPermission::getPermission)
                .extracting(PersistentEntity::getId)
                .containsExactly(rootPermission.getId());
    }

    @Test
    void mustUnlinkAllLowerPermissionFromGroupId() {
        linkGrandChildreenPermissionsToGroup();
        final var groupPermissionCreatedBeforeUnlinking = groupPermissionRepository.findByGroupId(group.getId(), null);

        // Unlink father permissions
        for (int i = 0; i < 5; i++) {
            final var permissionToUnlink = permissionRepository.findByAuthority("1." + i);
            linkPermissionToGroupService.unlinkLowerPermissionsByUpperPermission(group, permissionToUnlink);
        }

        Assertions.assertThat(groupPermissionCreatedBeforeUnlinking.getContent().size()).isEqualTo(25);
        final var groupPermissionByIdAfterRemovingLowerPermissions = groupPermissionRepository.findByGroupId(group.getId(), null);
        Assertions.assertThat(groupPermissionByIdAfterRemovingLowerPermissions.getContent()).isEmpty();
    }

    @Test
    void mustUnlinkAllChildreenWhenTheGrandFatherHasBeenLinked() {
        linkFatherPermissionsToGroup();

        linkPermissionToGroupService.linkPermissionToGroup(group.getId(), rootPermission.getId());

        final var permissionsOfGroup = groupPermissionRepository.findByGroupId(group.getId(), null);
        Assertions.assertThat(permissionsOfGroup.getContent())
                .extracting(GroupPermission::getPermission)
                .extracting(PersistentEntity::getId)
                .containsExactly(rootPermission.getId());
    }

    @Test
    void mustVerifyIfAllTheBrothersAreLinkedAndReturnTrue() {
        linkFatherPermissionsToGroup();
        final var sixChildPermission = new PermissionBuilder()
                .upperPermission(rootPermission)
                .build();
        permissionRepository.save(sixChildPermission);

        Assertions.assertThat(linkPermissionToGroupService.verifyIfAllTheBrothersAreLinked(group, sixChildPermission))
                .isTrue();
    }

    @Test
    void mustVerifyIfAllTheBrothersAreLinkedAndReturnFalse() {
        linkFatherPermissionsToGroup();
        final var sixChildPermission = new PermissionBuilder()
                .upperPermission(rootPermission)
                .build();
        permissionRepository.save(sixChildPermission);
        final var sevenChildPermission = new PermissionBuilder()
                .upperPermission(rootPermission)
                .build();
        permissionRepository.save(sevenChildPermission);

        Assertions.assertThat(linkPermissionToGroupService.verifyIfAllTheBrothersAreLinked(group, sixChildPermission))
                .isFalse();
    }

    private void linkGrandChildreenPermissionsToGroup() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                final var permissionToLink = permissionRepository.findByAuthority("1." + i + "." + j);
                final var groupPermission = GroupPermission.builder().permission(permissionToLink).group(group).build();
                groupPermissionRepository.save(groupPermission);
            }
        }
    }

    private void linkFatherPermissionsToGroup() {
        for (int i = 0; i < 5; i++) {
            final var permissionToLink = permissionRepository.findByAuthority("1." + i);
            final var groupPermission = GroupPermission.builder().permission(permissionToLink).group(group).build();
            groupPermissionRepository.save(groupPermission);
        }
    }

    private void insertTreeOfPermissions() {
        permissionRepository.save(rootPermission);
        for (int i = 0; i < 5; i++) {
            final var childPermission = new PermissionBuilder()
                    .authority("1." + i)
                    .upperPermission(rootPermission)
                    .build();
            permissionRepository.save(childPermission);
            for (int j = 0; j < 5; j++) {
                final var grandChildPermission = new PermissionBuilder()
                        .authority("1." + i + "." + j)
                        .upperPermission(childPermission)
                        .build();
                permissionRepository.save(grandChildPermission);
            }
        }
    }
}
