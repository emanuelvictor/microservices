package com.emanuelvictor.api.functional.accessmanager.domain.repositories;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.GroupPermission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @EntityGraph(attributePaths = {"group", "permission.id", "permission.name", "permission.authority", "permission.description"})
    @Query("select groupPermission " +
            " FROM GroupPermission groupPermission " +
            " WHERE groupPermission.group.id = :groupId ")
    List<GroupPermission> listByGroupId(final long groupId);

}
