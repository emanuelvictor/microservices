package com.emanuelvictor.api.functional.assessment.domain.services;

import com.emanuelvictor.api.functional.assessment.application.i18n.MessageSourceHolder;
import com.emanuelvictor.api.functional.assessment.domain.entities.Option;
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
    public Page<Option> listByFilters(final String defaultFilter, final Boolean enableFilter, final Pageable pageable) {
        return unityRepository.findAll(pageable);
    }

    /**
     * @param id long
     * @return User
     */
    @Transactional(readOnly = true)
    public Option findById(final long id) {
        return this.unityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", id)));
    }

    /**
     * @param option User
     * @return User
     */
    @Transactional
    public Option save(final Option option) {
        return this.unityRepository.save(option);
    }

    /**
     * @param option User
     * @return User
     */
    @Transactional
    public Option save(final long id, final Option option) {

        option.setId(id);

        final Option optionSaved = this.unityRepository.findById(option.getId())
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", option.getId())));

        return this.unityRepository.saveAndFlush(option);
    }

    /**
     * @param id long
     * @return User
     */
    @Transactional
    public Option updateEnable(final long id) {

        final Option optionSaved = this.unityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MessageSourceHolder.getMessage("repository.notFoundById", id)));

        Assert.notNull(optionSaved, MessageSourceHolder.getMessage("repository.notFoundById", id));

        return this.unityRepository.saveAndFlush(optionSaved);
    }

}
