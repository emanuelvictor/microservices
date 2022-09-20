package com.emanuelvictor.api.functional.flowcreator.domain.repositories;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.Option;
import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.AbstractRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class OptionRepositoryImpl extends AbstractRepository<Option, Long> implements OptionRepository {
}
