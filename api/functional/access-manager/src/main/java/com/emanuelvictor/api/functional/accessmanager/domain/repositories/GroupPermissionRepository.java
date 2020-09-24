package com.emanuelvictor.api.functional.accessmanager.domain.repositories;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long> {

    /**
     * @param groupId long
     * @return List<AccessGroupPermission>
     */
    List<GroupPermission> findByGroupId(final long groupId);

}
