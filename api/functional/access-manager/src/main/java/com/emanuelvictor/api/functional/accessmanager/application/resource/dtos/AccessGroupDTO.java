package com.emanuelvictor.api.functional.accessmanager.application.resource.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 09/10/2023
 */
@Setter
@Getter
@NoArgsConstructor
public class AccessGroupDTO implements Serializable {

    private Long id;
    private String name;
    private Set<AccessGroupPermissionDTO> groupPermissions;

}
