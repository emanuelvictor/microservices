package com.emanuelvictor.api.functional.assessment.domain.repositories;

import com.emanuelvictor.api.functional.assessment.domain.entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
@Repository
public interface UnityRepository extends JpaRepository<Option, Long> {

    /**
     * @param name String
     * @return Optional<Unity>
     */
    Optional<Option> findByName(final String name);

}
