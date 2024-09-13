package com.emanuelvictor.api.functional.address.domain.model.unit;

import com.emanuelvictor.api.functional.address.domain.model.assessment.UnitAssessmentType;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.link.Evaluable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"unit_assessment_type_id", "device_id"}),
        @UniqueConstraint(columnNames = {"unit_assessment_type_id", "device_id", "order"})
})
public class UnitAssessmentTypeQuiz extends AbstractEntity {

    /**
     * Ordem em que a avaliação será exibida no tablet
     */
    @Column
    private Short order;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_assessment_type_id")
    private UnitAssessmentType unitAssessmentType;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id")
    private Quiz quiz;

    /**
     *
     */
    private boolean active = true;

    /**
     *
     */
    @Transient
    private Set<Evaluable> evaluables;

}
