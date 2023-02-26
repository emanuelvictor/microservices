package com.emanuelvictor.api.functional.flowcreator.application.resources.generic;

import com.emanuelvictor.api.functional.flowcreator.domain.services.generic.AbstractService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * @param <T>
 */
@Transactional
public abstract class AbstractResource<T, ID extends Serializable> {

    /**
     *
     */
    @Autowired(required = false)
    protected AbstractService<T, ID> service;

    /**
     *
     */
    @Autowired
    protected JpaRepository<T, ID> repository;

    /**
     * @param entity T
     * @return T
     */
    @PostMapping
    public ResponseEntity<T> save(@RequestBody final T entity) {
        if (service == null)
            return ResponseEntity.ok(repository.save(entity));
        return ResponseEntity.ok(service.save(entity));
    }

    /**
     * @param entity T
     * @return T
     */
    @PutMapping("{id}")
    public ResponseEntity<T> save(@PathVariable("id") final ID id, @RequestBody final T entity) {
//        ((IEntity<ID>) entity).setId(id); todo
        if (service == null)
            return ResponseEntity.ok(repository.save(entity));
        return ResponseEntity.ok(service.save(entity));
    }

    /**
     * @param id {@link Long}
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") final ID id) {
        if (service == null)
            repository.deleteById(id);
        else
            service.deleteById(id);
        return ResponseEntity.ok(true);
    }

    /**
     * @param id {@link ID}
     * @return {@link Optional}
     */
    @GetMapping("{id}")
    public Optional<T> findById(@PathVariable("id") final ID id) {
        if (service == null)
            return repository.findById(id);
        return service.findById(id);
    }

    /**
     * @param pageable {@link Pageable}
     * @return {@link Page}
     */
    @GetMapping("all")
    public Page<T> findAll(final Pageable pageable) {
        if (service == null)
            return repository.findAll(pageable);
        return service.findAll(pageable);
    }
}