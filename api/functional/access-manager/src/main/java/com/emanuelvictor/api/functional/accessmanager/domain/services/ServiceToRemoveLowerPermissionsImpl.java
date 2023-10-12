package com.emanuelvictor.api.functional.accessmanager.domain.services;


import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;

import java.util.Collection;

public class ServiceToRemoveLowerPermissionsImpl implements ServiceToRemoveLowerPermissions {

    @Override
    public void remove(final Collection<GroupPermission> groupPermissions) {
//        groupPermissions.forEach(groupPermission -> groupPermission.getPermission().setLowerPermissions(null));
    }
}
