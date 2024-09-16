package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * TODO we don't need this service
 *
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupPermissionRepository accessGroupPermissionRepository;

    public GroupService(GroupRepository groupRepository, GroupPermissionRepository accessGroupPermissionRepository) {
        this.groupRepository = groupRepository;
        this.accessGroupPermissionRepository = accessGroupPermissionRepository;
    }

    /**
     * @param id    long
     * @param group {@link Group}
     * @return {@link Group}
     */
    public Group update(final long id, final Group group) {
        group.setId(id);
        return groupRepository.save(group);
    }

    /**
     * TODO make testes
     * @param id Long
     */
    public void delete(final long id) {
        accessGroupPermissionRepository.deleteAllByGroupId(id);
        groupRepository.deleteById(id);
    }
}
