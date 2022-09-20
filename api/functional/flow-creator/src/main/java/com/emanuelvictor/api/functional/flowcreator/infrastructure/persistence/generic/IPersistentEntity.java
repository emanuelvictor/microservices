package com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic;

import java.io.Serializable;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
public interface IPersistentEntity<ID extends Serializable> extends Serializable {

    /**
     * @return
     */
    ID getId();

    /**
     * @return
     */
    boolean isSaved();

    /**
     * @return
     */
    default boolean isNotSaved() {
        return !this.isSaved();
    }
}
