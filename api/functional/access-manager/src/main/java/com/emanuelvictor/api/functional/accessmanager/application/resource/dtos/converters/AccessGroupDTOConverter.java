package com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.converters;

import com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.AccessGroupDTO;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Group;

import java.util.HashMap;
import java.util.Map;

/**
 * Convert {@link Group} to {@link AccessGroupDTO}
 */
public class AccessGroupDTOConverter extends Converter<Group, AccessGroupDTO> {

    public AccessGroupDTOConverter() {
        this.pool = new HashMap<>();
    }

    public AccessGroupDTOConverter(Map<Object, Object> pool) {
        this.pool = pool;
    }

    @Override
    public Group convert(final AccessGroupDTO origin) {

        final Group destiny = convertWithoutRecursive(origin);

        final AccessGroupPermissionDTOConverter groupPermissionDTOConverter = new AccessGroupPermissionDTOConverter(pool);
//        if (origin.getGroupPermissions() != null)
//            destiny.setGroupPermissions(groupPermissionDTOConverter.convertRecursive(origin.getGroupPermissions()));

        return destiny;
    }

    @Override
    public Group convertWithoutRecursive(final AccessGroupDTO origin) {
        final Group accessGroup = new Group();
//        accessGroup.setName(origin.getName());
        accessGroup.setId(origin.getId());
        return accessGroup;
    }
}
