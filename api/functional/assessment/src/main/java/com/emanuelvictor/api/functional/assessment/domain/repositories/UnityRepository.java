package com.emanuelvictor.api.functional.assessment.domain.repositories;

import com.emanuelvictor.api.functional.assessment.domain.entities.Unity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 01/01/2020
 */
@Repository
public interface UnityRepository extends JpaRepository<Unity, Long> {

    /**
     * @param name String
     * @return Optional<Unity>
     */
    Optional<Unity> findByName(final String name);

}
