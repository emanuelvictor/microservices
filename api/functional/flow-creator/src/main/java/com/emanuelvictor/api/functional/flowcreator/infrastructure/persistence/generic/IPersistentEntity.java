package com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic;

import java.io.Serializable;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
public interface IPersistentEntity<ID extends Serializable> extends Serializable {

    /**
     * @return {@link ID}
     */
    ID getId();

    /**
     * @param id {@link ID}
     */
    void setId(final ID id);

    /**
     * @return {@link Boolean}
     */
    boolean isSaved();

    /**
     * @return {@link Boolean}
     */
    default boolean isNotSaved() {
        return !this.isSaved();
    }
}
