package com.emanuelvictor.api.functional.assessment.domain.services;

import com.emanuelvictor.api.functional.assessment.application.i18n.MessageSourceHolder;
import com.emanuelvictor.api.functional.assessment.domain.entities.Unity;
import com.emanuelvictor.api.functional.assessment.domain.repositories.UnityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 04/01/2020
 */
@Component
@RequiredArgsConstructor
public class UnityService {

    /**
     *
     */
    private final UnityRepository unityRepository;

    /**
     * @param defaultFilter String
     * @param enableFilter  Boolean
     * @param pageable      Pageable
     * @return Page<User>
     */
    public Page<Unity> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return unityRepository.findAll(pageable);
    }

    /**
     * @param id long
     * @return User
     */
    @Transactional(readOnly = true)
    public Unity findById(final long id) {
        return this.unityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", id)));
    }

    /**
     * @param unity User
     * @return User
     */
    @Transactional
    public Unity save(final Unity unity) {
        return this.unityRepository.save(unity);
    }

    /**
     * @param unity User
     * @return User
     */
    @Transactional
    public Unity save(final long id, final Unity unity) {

        unity.setId(id);

        final Unity unitySaved = this.unityRepository.findById(unity.getId())
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", unity.getId())));

        return this.unityRepository.saveAndFlush(unity);
    }

    /**
     * @param id long
     * @return User
     */
    @Transactional
    public Unity updateEnable(final long id) {

        final Unity unitySaved = this.unityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", id)));

        Assert.notNull(unitySaved, MessageSourceHolder.getMessage("repository.notFoundById", id));

        return this.unityRepository.saveAndFlush(unitySaved);
    }

}
