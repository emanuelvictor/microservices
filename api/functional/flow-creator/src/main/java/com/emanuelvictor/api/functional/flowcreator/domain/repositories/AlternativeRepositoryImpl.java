package com.emanuelvictor.api.functional.flowcreator.domain.repositories;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Alternative;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class AlternativeRepositoryImpl extends AbstractRepository<Alternative, Long> implements AlternativeRepository {
}
