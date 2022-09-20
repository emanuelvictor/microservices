package com.emanuelvictor.api.functional.flowcreator.domain.entities;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Setter
@Getter
@lombok.EqualsAndHashCode(callSuper = true)
public class Alternative extends AbstractAlternative  {

    /**
     *
     */
    private String messageToNext;

    /**
     *
     */
    private AbstractAlternative previous;

    /**
     *
     */
    private Option option;

}
