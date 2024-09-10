package com.emanuelvictor.api.functional.accessmanager.application.resource.group;

import com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.AccessGroupDTO;
import com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.AccessGroupPermissionDTO;
import com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.converters.AccessGroupDTOConverter;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import com.emanuelvictor.api.functional.accessmanager.domain.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
// TODO remove this. Its can be kept on GroupResource maybe. For now
@RestController
@RequiredArgsConstructor
public class UpdateAccessGroupResource {

    /**
     *
     */
    private final GroupService accessGroupService;

    /**
     * @param id             Long
     * @param accessGroupDTO AccessGroupDTO
     * @return group
     */
    @PutMapping("groups/{id}")
    @PreAuthorize("hasAnyAuthority('root.access-manager.groups.put','root.access-manager.groups','root.access-manager','root')")
    public Object save(@PathVariable final long id, @RequestBody final AccessGroupDTO accessGroupDTO) {
//        accessGroupDTO.getGroupPermissions().forEach(accessGroupPermission ->
//                accessGroupPermission.setGroup(accessGroupDTO)
//        );

        final AccessGroupDTOConverter accessGroupDTOConverter = new AccessGroupDTOConverter();

        final Group accessGroup = accessGroupDTOConverter.convert(accessGroupDTO);

        return accessGroupService.save(id, accessGroup);
    }
}
