package com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence;

import com.emanuelvictor.api.functional.flowcreator.infrastructure.persistence.generic.IPersistentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractRepository<T extends IPersistentEntity, ID> implements CrudRepository<T, ID> {

    /**
     *
     */
    private final List<T> collection = new ArrayList<>();

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity; will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    public <S extends T> S save(S entity) {
        collection.add(entity);
        return entity;
    }

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null} nor must it contain {@literal null}.
     * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
     * as the {@literal Iterable} passed as an argument.
     * @throws IllegalArgumentException in case the given {@link Iterable entities} or one of its entities is
     *                                  {@literal null}.
     */
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    public Optional<T> findById(ID id) {
        return collection.stream().filter(t -> t.getId().equals(id)).collect(Collectors.toList()).stream().findFirst(); // TODO Acoplamento, Must have annotations to use reflection
    }

    /**
     * @return Stream<T>
     */
    @Override
    public Stream<T> findAll() {
        return collection.stream();
    }
}
