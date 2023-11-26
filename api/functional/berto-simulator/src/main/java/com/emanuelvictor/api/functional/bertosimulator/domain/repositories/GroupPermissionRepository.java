package com.emanuelvictor.api.functional.bertosimulator.domain.repositories;

import com.emanuelvictor.api.functional.bertosimulator.domain.entities.GroupPermission;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermission, Long> {

    /**
     * @param groupId  {@link Long}
     * @param pageable {@link Pageable}
     * @return {@link Page}
     */
    Page<GroupPermission> findByGroupId(final long groupId, final Pageable pageable);

    /**
     * FIxme MAKE TESTS
     *
     * @param upperPermissionId {@link Long}
     * @param groupId           {@link Long}
     * @param pageable          {@link Pageable}
     * @return {@link Page}
     */
    @Query("from GroupPermission groupPermission where groupPermission.group.id = :groupId AND groupPermission.permission.upperPermission.id = :upperPermissionId")
    Page<GroupPermission> findByUpperPermissionIdAndGroupId(final long upperPermissionId, final long groupId, final Pageable pageable);

    /**
     * FIxme MAKE TESTS
     *
     * @param groupId      {@link Long}
     * @param permissionId {@link Long}
     */
    @Modifying
    @Transactional
    @Query("delete from GroupPermission groupPermission where groupPermission.group.id = :groupId AND groupPermission.permission.id = :permissionId")
    void deleteByGroupIdAndPermissionId(Long groupId, Long permissionId);
}
