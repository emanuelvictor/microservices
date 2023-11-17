package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.PermissionRepository;
import jakarta.transaction.Transactional;

public class LinkPermissionToGroupService {

    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;
    private final GroupPermissionRepository groupPermissionRepository;


    public LinkPermissionToGroupService(GroupRepository groupRepository, PermissionRepository permissionRepository, GroupPermissionRepository groupPermissionRepository) {
        this.groupRepository = groupRepository;
        this.permissionRepository = permissionRepository;
        this.groupPermissionRepository = groupPermissionRepository;
    }

    //    @Transactional
    public void linkPermissionToGroup(final long groupId, final long permissionId) {
        final var group = groupRepository.findById(groupId).orElseThrow();
        final var permission = permissionRepository.findById(permissionId).orElseThrow();

        unlinkLowerPermissionsByUpperPermission(group, permission);

        link(group, permission);
    }

    void unlinkLowerPermissionsByUpperPermission(final Group group, final Permission permission) {
        final var lowerPermissions = permissionRepository
                .findByUpperPermissionId(permission.getId(), null).getContent();
        lowerPermissions.forEach(lowerPermission -> unlinkLowerPermissionsByUpperPermission(group, lowerPermission));
        groupPermissionRepository.deleteByGroupIdAndPermissionId(group.getId(), permission.getId());
    }

    boolean verifyIfAllTheBrothersAreLinked(final Group group, final Permission permission) {
        final var upperPermissionId = permission.getUpperPermission().getId();
        final var countNextPermissions = permissionRepository
                .findByUpperPermissionId(upperPermissionId, null).getSize();
        final var countNextLinkedPermissions = groupPermissionRepository
                .findByUpperPermissionIdAndGroupId(upperPermissionId, group.getId(), null).getSize();
        return countNextLinkedPermissions == (countNextPermissions - 1);
    }

    GroupPermission link(final Group group, final Permission permission) {
        final GroupPermission groupPermission = GroupPermission.builder().permission(permission).group(group).build();
        return groupPermissionRepository.save(groupPermission);
    }

}
