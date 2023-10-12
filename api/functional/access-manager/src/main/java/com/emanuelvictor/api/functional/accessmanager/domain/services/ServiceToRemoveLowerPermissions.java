package com.emanuelvictor.api.functional.accessmanager.domain.services;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;

import java.util.Collection;

// TODO need testse
@FunctionalInterface
public interface ServiceToRemoveLowerPermissions {

    void remove(final Collection<GroupPermission> groupPermissions);
}
