package com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.converters;

import com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.PermissionDTO;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;

import java.util.Map;

/**
 * Convert {@link Permission} to {@link PermissionDTO}
 */
public class PermissionDTOConverter extends Converter<Permission, PermissionDTO> {

    public PermissionDTOConverter(Map<Object, Object> pool) {
        this.pool = pool;
    }

    @Override
    public Permission convert(final PermissionDTO origin) {

        final Permission permission = convertWithoutRecursive(origin);

//        if (permission.getLowerPermissions() != null) {
////            final PermissionDTOConverter permissionDTOConverter = new PermissionDTOConverter(pool);
//            permission.setLowerPermissions(convertRecursive(origin.getLowerPermissions()));
//        }
//
//        if (permission.getUpperPermission() != null)
//            permission.setUpperPermission(convertRecursive(origin.getUpperPermission()));

        return permission;
    }

    @Override
    public Permission convertWithoutRecursive(final PermissionDTO origin) {
        final Permission permission = new Permission();
        permission.setId(origin.getId());
//        permission.setName(origin.getName());
//        permission.setAuthority(origin.getAuthority());
//        permission.setDescription(origin.getDescription());
        return permission;
    }
}
