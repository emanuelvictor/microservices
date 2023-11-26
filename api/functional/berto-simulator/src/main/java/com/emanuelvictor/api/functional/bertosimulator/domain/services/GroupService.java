package com.emanuelvictor.api.functional.bertosimulator.domain.services;

import com.emanuelvictor.api.functional.bertosimulator.domain.entities.Group;
import com.emanuelvictor.api.functional.bertosimulator.domain.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Component
@RequiredArgsConstructor
public class GroupService {

    /**
     *
     */
    private final GroupRepository groupRepository;

    /**
     * @param defaultFilter String
     * @param pageable      Pageable
     * @return Page<AccessGroup>
     */
    public Page<Group> listByFilters(final String defaultFilter, final Pageable pageable) {
        return this.groupRepository.listByFilters(defaultFilter, pageable);
    }

    /**
     * @param id long
     * @return {@link Optional <AccessGroup>}
     */
    public Optional<Group> findById(final long id) {
        return this.groupRepository.findById(id);
    }

    /**
     * @param group {@link Group}
     * @return {@link Group}
     */
    @Transactional
    public Group save(final Group group) {
        return groupRepository.save(group);
    }

    /**
     * @param id    long
     * @param group {@link Group}
     * @return {@link Group}
     */
    public Group save(final long id, final Group group) {
        group.setId(id);
        return groupRepository.save(group);
    }

    /**
     * @param id Long
     */
    public void delete(final long id) {
        this.groupRepository.deleteById(id);
    }
}
