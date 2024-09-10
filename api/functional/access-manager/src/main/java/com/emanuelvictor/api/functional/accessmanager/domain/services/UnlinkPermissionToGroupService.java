package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.GroupRepository;
import com.emanuelvictor.api.functional.accessmanager.domain.repositories.PermissionRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 09/09/2022
 * <p>
 * This service contains the algorithms to unlink a Permission from AccessGroup.
 * The service with the link algorithms is {@link LinkPermissionToGroupService}
 */
@Transactional // TODO the transaction must be kept on Application Layer
public class UnlinkPermissionToGroupService {

    private final PermissionRepository permissionRepository;
    private final GroupPermissionRepository groupPermissionRepository;
    private final LinkPermissionToGroupService linkPermissionToGroupService;

    public UnlinkPermissionToGroupService(GroupRepository groupRepository, PermissionRepository permissionRepository,
                                          GroupPermissionRepository groupPermissionRepository, LinkPermissionToGroupService linkPermissionToGroupService) {
        this.permissionRepository = permissionRepository;
        this.groupPermissionRepository = groupPermissionRepository;
        this.linkPermissionToGroupService = linkPermissionToGroupService;
    }

    public void unlinkPermissionToGroup(final long groupId, final String authority) {
        groupPermissionRepository.findByGroupIdAndPermissionAuthority(groupId, authority)
                .ifPresentOrElse(groupPermission -> groupPermissionRepository.deleteByGroupIdAndPermissionAuthority(groupId, authority),
                        () -> {
                            // não encontrei?
                            // marco os irmãos
                            // subo para o nível de cima,
                            // ainda não encontrei?
                            // marco os irmãos
                            // subo para o de cima
                            // Encontrei?
                            // Desmarco este (ifPresentOrElse(groupPermission -> unlinkLowerPermis ;.... tra lá lá, primeira condicional)
                            final Permission permission = permissionRepository.findByAuthority(authority).orElseThrow();
                            permissionRepository.findByUpperPermissionId(permission.getUpperPermission().getId(), null) // todo criar uma query que retorne apenas os irmãos diretamente
                                    .forEach(siblingPermission -> {
                                        if (!siblingPermission.getAuthority().equals(permission.getAuthority())) {
                                            linkPermissionToGroupService.link(groupId, siblingPermission.getAuthority()); // TODO verify if it's can be only linkPermissionToGroupService.link
                                        }
                                    });
                            unlinkPermissionToGroup(groupId, permission.getUpperPermission().getAuthority());
                        });
    }
}
