package com.emanuelvictor.api.functional.accessmanager.domain.entities;

import com.emanuelvictor.api.functional.accessmanager.domain.entities.generic.EntityIdResolver;
import com.emanuelvictor.api.functional.accessmanager.domain.entities.generic.PersistentEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Permission.class,
        resolver = EntityIdResolver.class)
public class Permission extends PersistentEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String authority;

    private String description;

    @JsonProperty
    @ManyToOne(fetch = FetchType.EAGER)
    private Permission upperPermission;

    @Builder
    public Permission(Long id, String name, String authority, String description, Permission upperPermission) {
        super(id);
        this.name = name;
        this.authority = authority;
        this.description = description;
        this.upperPermission = upperPermission;
    }
}
