package com.emanuelvictor.api.functional.address.domain.model.evaluableitem.link;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.unit.UnitAssessmentTypeQuiz;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.EvaluableItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@lombok.EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"evaluable_item_id", "unit_assessment_type_quiz_device_id"})
})
public class Evaluable extends AbstractEntity {

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    private EvaluableItem evaluableItem;

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    private UnitAssessmentTypeQuiz unitAssessmentTypeQuiz;

    /**
     *
     */
    private boolean active /*= false*/;

    /**
     *
     */
    @PrePersist
    public void prePersist() {
        this.active = true;
    }

}
