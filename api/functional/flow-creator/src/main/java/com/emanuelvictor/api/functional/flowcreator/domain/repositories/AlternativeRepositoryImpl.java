package com.emanuelvictor.api.functional.flowcreator.domain.repositories;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Alternative;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Repository
public class AlternativeRepositoryImpl extends AbstractRepository<Alternative, Long> implements AlternativeRepository {

    /**
     * @param previousId {@link Long}
     * @return {@link List}
     */
    @Override
    public List<Alternative> findByPreviousId(final Long previousId) {
        return collection.stream().filter(alternative -> alternative.getPrevious() != null && alternative.getPrevious().getId().equals(previousId)).collect(Collectors.toList());
    }
}
