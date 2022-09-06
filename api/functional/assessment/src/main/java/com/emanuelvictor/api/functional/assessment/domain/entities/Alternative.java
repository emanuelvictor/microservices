package com.emanuelvictor.api.functional.assessment.domain.entities;

import com.emanuelvictor.api.functional.assessment.domain.entities.generic.EntityIdResolver;
import com.emanuelvictor.api.functional.assessment.domain.entities.generic.PersistentEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Data
@Entity
@Audited
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Alternative.class,
        resolver = EntityIdResolver.class
)
public class Alternative extends PersistentEntity {

    /**
     *
     */
    @NotBlank
    @Column(nullable = false)
    private String messageToNext;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Alternative oldChoice;

    /**
     * d
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Option option;

    /**
     * @param messageToNext String
     */
    public Alternative(final String messageToNext) {
        this.messageToNext = messageToNext;
    }

    /**
     * @param messageToNext String
     * @param option         Unity
     */
    public Alternative(final String messageToNext, final Option option) {
        this(messageToNext);
        this.option = option;
    }

    /**
     * @param messageToNext String
     * @param oldChoice     Alternative
     */
    public Alternative(final String messageToNext, final Alternative oldChoice) {
        this(messageToNext);
        this.oldChoice = oldChoice;
    }

    /**
     * @param messageToNext String
     * @param oldChoice     Alternative
     */
    public Alternative(final String messageToNext, final Option option, final Alternative oldChoice) {
        this(messageToNext, option);
        this.oldChoice = oldChoice;
    }
}
