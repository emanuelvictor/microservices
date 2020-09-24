package com.emanuelvictor.api.functional.accessmanager.application.resource;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.emanuelvictor.api.functional.accessmanager.application.resource.Roles.*;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping({GROUP_MAPPING_RESOURCE})
public class GroupResource {

    /**
     *
     */
    private final GroupService groupService;

    /**
     * @param defaultFilter String
     * @param pageable      Pageable
     * @return Page<group>
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('" + GROUP_GET_ROLE + "')")
    public Page<Group> listByFilters(final String defaultFilter, final Pageable pageable) {
        return this.groupService.listByFilters(defaultFilter, pageable);
    }

    /**
     * @param id long
     * @return Optional<group>
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + GROUP_GET_ROLE + "')")
    public Optional<Group> findById(@PathVariable final long id) {
        return this.groupService.findById(id);
    }

    /**
     * @param grupoAcesso group
     * @return group
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('" + GROUP_POST_ROLE + "')")
    public Group save(@RequestBody final Group grupoAcesso) {
        grupoAcesso.getGroupPermissions().forEach(grupoAcessoPermissao ->
                grupoAcessoPermissao.setGroup(grupoAcesso)
        );
        return this.groupService.save(grupoAcesso);
    }

    /**
     * @param id          long
     * @param grupoAcesso group
     * @return group
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('" + GROUP_PUT_ROLE + "')")
    public Group save(@PathVariable final long id, @RequestBody final Group grupoAcesso) {
        grupoAcesso.getGroupPermissions().forEach(grupoAcessoPermissao ->
                grupoAcessoPermissao.setGroup(grupoAcesso)
        );
        return this.groupService.save(id, grupoAcesso);
    }

    /**
     * @param id long
     * @return Boolean
     */
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('" + GROUP_DELETE_ROLE + "')")
    public Boolean delete(@PathVariable final long id) {
        this.groupService.delete(id);
        return true;
    }

}
