package com.emanuelvictor.api.functional.flowcreator.domain.services.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @param <T>
 */
public abstract class AbstractService<T, ID extends Serializable> {

    /**
     *
     */
    @Autowired // TODO couple, must use inject from jakarta
    protected JpaRepository<T, ID> repository;

    /**
     * @param entity T
     * @return T
     */
    public T save(final T entity) {
        return repository.save(entity);
    }


    /**
     * @param id {@link ID}
     */
    public Optional<T> findById(final ID id) {
        return repository.findById(id);
    }

    /**
     * @param id {@link ID}
     */
    public void deleteById(final ID id) {
        repository.deleteById(id);
    }

    /**
     * @param pageable {@link Pageable}
     * @return {@link Page}
     */
    public Page<T> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }
}