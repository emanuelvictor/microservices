package com.emanuelvictor.api.functional.address.domain.model.assessment;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.EvaluableItem;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.link.Evaluable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"evaluable_id", "assessment_id"})
})
public class AssessmentEvaluable extends AbstractEntity {

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    /**
     *
     */
    @ManyToOne(optional = false)
    private Evaluable evaluable;

    /**
     * @param id         long
     * @param nameOfUser String
     */
    public AssessmentEvaluable(final long id, final String nameOfUser) {
        super(id);

        final EvaluableItem evaluableItem = new EvaluableItem();
        evaluableItem.setName(nameOfUser);

        final Evaluable evaluable = new Evaluable();
        evaluable.setEvaluableItem(evaluableItem);

        this.evaluable = evaluable;
    }

    /**
     * @param id long
     */
    public AssessmentEvaluable(final long id) {
        super(id);
    }
}
