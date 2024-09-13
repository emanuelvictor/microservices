package com.emanuelvictor.api.functional.address.domain.model.evaluableitem;

import lombok.Data;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@lombok.EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Person extends AbstractEntity {

    /**
     *
     */
    @NotEmpty
    @NotNull
    @Column(nullable = false)
    protected String name;

    /**
     *
     */
    @Column(unique = true)
    protected String document;

    /**
     *
     */
    @Transient
    protected Object average;

    /**
     *
     */
    @Transient
    protected long countAssessments;

    /**
     *
     */
    @Transient
    protected long assessmentsOne;

    /**
     *
     */
    @Transient
    protected long assessmentsTwo;

    /**
     *
     */
    @Transient
    protected long assessmentsThree;

    /**
     *
     */
    @Transient
    protected long assessmentsFour;

    /**
     *
     */
    @Transient
    protected long assessmentsFive;

}
