package com.emanuelvictor.api.functional.flowcreator.domain.entities;

import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.PersistentEntity;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Setter
@Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class Option extends PersistentEntity {

    /**
     *
     */
    private String name;

    /**
     * @param name String
     */
    public Option(final String name) {
        this.name = name;
    }
}
