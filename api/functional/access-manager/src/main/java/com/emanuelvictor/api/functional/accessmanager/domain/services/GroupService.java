package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Component
@RequiredArgsConstructor
public class GroupService {

    /**
     *
     */
    private final GroupRepository groupRepository;

    /**
     *
     */
    private final GroupPermissionRepository groupPermissionRepository;

    /**
     *
     */
    private final ServiceToRemoveLowerPermissions serviceToRemoveLowerPermissions;

    /**
     * @param defaultFilter String
     * @param pageable      Pageable
     * @return Page<AccessGroup>
     */
    public Page<Group> listByFilters(final String defaultFilter, final Pageable pageable) {
        return this.groupRepository.listByFilters(defaultFilter, pageable);
    }

    /**
     * @param id long
     * @return {@link Optional <AccessGroup>}
     */
    public Optional<Group> findById(final long id) {
        return this.groupRepository.findById(id);
    }

    /**
     * @param group {@link Group}
     * @return {@link Group}
     */
    @Transactional
    public Group save(final Group group) {
//        Assert.notEmpty(group.getGroupPermissions(), "Defina permissões para esse Grupo de Acesso");
        return groupRepository.save(group);
    }

    /**
     * @param id    long
     * @param group {@link Group}
     * @return {@link Group}
     */
    public Group save(final long id, final Group group) {
        group.setId(id);

        Assert.notEmpty(group.getGroupPermissions(), "Defina permissões para esse Grupo de Acesso");

        /*
         * Lista auxiliar com os grupos acesso permissao que serão persistidos a posteriore
         */
        final Set<GroupPermission> groupPermissionList = group.getGroupPermissions();

        /*
         * Seto null nos grupos acesso permissoes, dessa forma o entityManager remove todos via cascade
         */
        group.setGroupPermissions(new HashSet<>());

        /*
         * Atualizo o grupo de acesso
         */
        groupRepository.save(group);

//        // Remove as permissões desnecessárias
//        serviceToRemoveLowerPermissions.remove(groupPermissionList);

        /*
         * Insiro todos os grupo acesso permissao
         */
        groupPermissionList.forEach(groupPermission -> {
            if (groupPermission.getId() == null)
                groupPermission.setId(null);
            groupPermissionRepository.save(groupPermission);
        });

        return group;
    }

    /**
     * @param id Long
     * @return Boolean
     */
    public Boolean delete(final long id) {
        this.groupRepository.deleteById(id);
        return true;
    }
}
