package com.emanuelvictor.api.functional.flowcreator.domain.services.generic;

import com.emanuelvictor.api.functional.flowcreator.domain.entities.generic.UniqueIdentifierJava;
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
    @Autowired(required = false) // TODO couple, must use inject from jakarta
    private JpaRepository<T, ID> repository;

    /**
     *
     */
    @Autowired(required = false) // TODO couple, must use inject from jakarta
    private JpaRepository<T, UniqueIdentifierJava<ID>> typedIdentifierRepository;

    public JpaRepository<T, ?> getRepository(){
        if(repository == null)
            return typedIdentifierRepository;
        return repository;
    }

    /**
     * @param entity T
     * @return T
     */
    public T save(final T entity) {
        return getRepository().save(entity);
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
     * @param id {@link ID}
     */
    public Optional<T> findById(final UniqueIdentifierJava<ID> id) {
        return typedIdentifierRepository.findById(id);
    }

    /**
     * @param id {@link ID}
     */
    public void deleteById(final UniqueIdentifierJava<ID> id) {
        typedIdentifierRepository.deleteById(id);
    }

    /**
     * @param pageable {@link Pageable}
     * @return {@link Page}
     */
    public Page<T> findAll(final Pageable pageable) {
        return getRepository().findAll(pageable);
    }
}