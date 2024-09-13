package com.emanuelvictor.api.functional.address.domain.model.unit;

import com.emanuelvictor.api.functional.address.domain.model.address.Address;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Unit extends Person {

    /**
     *
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    /**
     *
     */
    private String thanksText;

    /**
     *
     */
    public Unit(final long id, final String name, final String document, final Address address) {

        this.id = id;
        this.name = name;
        this.document = document;

        this.address = address;

    }

    /**
     *
     */
    public Unit(final long id, final String name, final String document, final Address address,
                final Double average, final long countAssessments, final long assessmentsOne, final long assessmentsTwo, final long assessmentsThree, final long assessmentsFour, final long assessmentsFive) {

        this.id = id;
        this.name = name;
        this.document = document;

        this.address = address;

        this.average = average;
        this.countAssessments = countAssessments;
        this.assessmentsOne = assessmentsOne;
        this.assessmentsTwo = assessmentsTwo;
        this.assessmentsThree = assessmentsThree;
        this.assessmentsFour = assessmentsFour;
        this.assessmentsFive = assessmentsFive;
    }

}
