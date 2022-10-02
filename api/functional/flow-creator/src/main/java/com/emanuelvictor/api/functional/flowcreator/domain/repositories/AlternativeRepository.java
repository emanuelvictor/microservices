package com.emanuelvictor.api.functional.flowcreator.domain.repositories;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Alternative;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.CrudRepository;

import java.util.List;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
public interface AlternativeRepository extends CrudRepository<Alternative, Long> {

    /**
     * @param previousId {@link Long}
     * @return {@link List}
     */
    List<Alternative> findByPreviousId(final Long previousId);
}
