package com.emanuelvictor.api.functional.accessmanager.domain.entities;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.generic.EntityIdResolver;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.generic.PersistentEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Permission.class,
        resolver = EntityIdResolver.class)
public class Permission extends PersistentEntity {

    /**
     *
     */
    @NotBlank
    @Column(nullable = false)
    private String name;

    /**
     *
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String authority;

    /**
     *
     */
    private String description;

    /**
     *
     */
    @JsonProperty
    @ManyToOne(fetch = FetchType.EAGER)
    private Permission upperPermission;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "upperPermission")
    private Set<Permission> lowerPermissions;

    /**
     *
     */
    public Permission() {
    }

    /**
     * @param authority String
     */
    public Permission(@NotNull final String authority) {
        this.authority = authority;
    }

    /**
     * @param authority        String
     * @param upperPermission  Permission
     * @param lowerPermissions Set<Permission>
     */
    public Permission(final String authority,
                      final Permission upperPermission,
                      final Set<Permission> lowerPermissions) {
        this.authority = authority;
        this.upperPermission = upperPermission;
        this.lowerPermissions = lowerPermissions;
    }

//    public Permission(final Long id, final String name, final String authority, final String description) {
//        this.id = id;
//        this.name = name;
//        this.authority = authority;
//        this.description = description;
//    }

    /**
     * @return Permission
     */
    public Permission copy() {
        return new Permission(authority, upperPermission, lowerPermissions);
    }
}
