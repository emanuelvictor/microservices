package com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic;

import lombok.*;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
@ToString
@NoArgsConstructor
public abstract class PersistentEntity implements IPersistentEntity<Long> {

    /**
     *
     */
    @Getter
    @Setter
    protected Long id;

    /**
     * @param id Long
     */
    public PersistentEntity(final Long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc }
     *
     * @return
     */
    @Override
    public boolean isSaved() {
        return this.id != null && this.id != 0;
    }
}
