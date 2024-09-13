package com.emanuelvictor.api.functional.address.domain.model.evaluableitem.link;

import lombok.Data;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.unit.Unit;
import com.emanuelvictor.api.functional.address.domain.model.evaluableitem.EvaluableItem;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@lombok.EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"evaluable_item_id", "unit_id"})
})
public class Operator extends AbstractEntity {

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
    private Unit unit;

}
