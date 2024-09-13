package com.emanuelvictor.api.functional.accessmanager.application.resource.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 09/10/2023
 */
@Setter
@Getter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class PermissionDTO implements Serializable {

    private Long id;
    private String name;
    private String authority;
    private String description;
    private PermissionDTO upperPermission;
    private Set<PermissionDTO> lowerPermissions;

}
