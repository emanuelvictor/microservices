package com.emanuelvictor.api.functional.accessmanager.domain.repositories;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /**
     * @param filter   String
     * @param branch   Boolean
     * @param pageable Pageable
     * @return Page<Permission>
     */
    @Query("FROM Permission permission WHERE" +
            "   (   " +
            "       (" +
            "               FILTER(:filter, permission.authority) = TRUE" +
            "       )" +
            "   )" +
            "   AND " +
            "   ((:upperPermissionId IS NOT NULL AND permission.upperPermission.id = :upperPermissionId) OR (:upperPermissionId IS NULL))" +
            "   AND " +
            "   ((:branch = TRUE AND permission.upperPermission.id IS NULL) OR (:branch IS NULL))"
    )
    Page<Permission> listByFilters(@Param("filter") final String filter,
                                   @Param("upperPermissionId") final Long upperPermissionId,
                                   @Param("branch") final Boolean branch,
                                   final Pageable pageable);
}
