package com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.converters;

import com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.AccessGroupPermissionDTO;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;

import java.util.Map;

/**
 * Convert {@link GroupPermission} to {@link AccessGroupPermissionDTO}
 */
public class AccessGroupPermissionDTOConverter extends Converter<GroupPermission, AccessGroupPermissionDTO> {

    public AccessGroupPermissionDTOConverter(Map<Object, Object> pool) {
        super(pool);
    }

    @Override
    public GroupPermission convert(final AccessGroupPermissionDTO origin) {

        final GroupPermission destiny = convertWithoutRecursive(origin);

//        final PermissionDTOConverter permissionDTOConverter = new PermissionDTOConverter(pool);
//        destiny.setPermission(permissionDTOConverter.convertRecursive(origin.getPermission()));
//        final AccessGroupDTOConverter accessGroupDTOConverter = new AccessGroupDTOConverter(pool);
//        destiny.setGroup(accessGroupDTOConverter.convertRecursive(origin.getGroup()));

        return destiny;
    }

    @Override
    public GroupPermission convertWithoutRecursive(final AccessGroupPermissionDTO origin) {
        final GroupPermission groupPermission = new GroupPermission();
        groupPermission.setId(origin.getId());
        return groupPermission;
    }
}
