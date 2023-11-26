package com.emanuelvictor.api.functional.bertosimulator.application.resource;

import com.emanuelvictor.api.functional.bertosimulator.domain.entities.Group;
import com.emanuelvictor.api.functional.bertosimulator.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.bertosimulator.domain.repositories.GroupPermissionRepository;
import com.emanuelvictor.api.functional.bertosimulator.domain.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping({"v1/groups", "v1/access-groups"})
public class GroupResource {

    /**
     *
     */
    private final GroupService groupService;

    /**
     *
     */
    private final GroupPermissionRepository groupPermissionRepository;

    /**
     * @param defaultFilter String
     * @param pageable      Pageable
     * @return Page<group>
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('root.access-manager.groups.get','root.access-manager.groups','root.access-manager','root')")
    public Page<Group> listByFilters(final String defaultFilter, final Pageable pageable) {
        return groupService.listByFilters(defaultFilter, pageable);
    }

    /**
     * @param id long
     * @return Optional<group>
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('root.access-manager.groups.get','root.access-manager.groups','root.access-manager','root')")
    public Optional<Group> findById(@PathVariable final long id) {
        return groupService.findById(id);
    }

    /**
     * @param grupoAcesso group
     * @return group
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('root.access-manager.groups.post','root.access-manager.groups','root.access-manager','root')")
    public Group save(@RequestBody final Group grupoAcesso) {
        return groupService.save(grupoAcesso);
    }

    /**
     * @param id long
     * @return Boolean
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('root.access-manager.groups.delete','root.access-manager.groups','root.access-manager','root')")
    public Boolean delete(@PathVariable final long id) {
        groupService.delete(id);
        return true;
    }

    /**
     * @param id long
     * @return Page<GroupPermission>
     */
    @GetMapping("{id}/access-group-permissions")
    @PreAuthorize("hasAnyAuthority('root.access-manager.groups.get','root.access-manager.groups','root.access-manager','root')")
    public Page<GroupPermission> findAccessGroupPermissionsByUserId(@PathVariable final long id,
                                                                    final Pageable pageable) {
        return groupPermissionRepository.findByGroupId(id, pageable);
    }

}
