package com.emanuelvictor.api.functional.accessmanager.domain.entities;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.generic.PersistentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"group_permission_id", "permission_id"})
})
public class GroupPermission extends PersistentEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Permission permission;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_permission_id")
    private Group group;

//    public GroupPermission(final Long id, final Group group, final Long permissionId, final String permissionName, final String permissionAuthority, final String permissionDescription) {
//        this.id = id;
//        this.group = group;
//        this.permission = new Permission(permissionId, permissionName, permissionAuthority, permissionDescription);
//    }
}
