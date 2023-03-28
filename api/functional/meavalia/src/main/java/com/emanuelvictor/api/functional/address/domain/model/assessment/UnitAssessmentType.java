package com.emanuelvictor.api.functional.address.domain.model.assessment;

import lombok.Data;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.unit.Unit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"assessment_type_id", "unit_id"}),
        @UniqueConstraint(columnNames = {"unit_id", "order"})
})
public class UnitAssessmentType extends AbstractEntity {

    /**
     * TODO remover
     * Ordem em que a avaliação será exibida no tablet
     */
    @Column
    private Short order;

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "assessment_type_id")
    private AssessmentType assessmentType;

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    private Unit unit;

//    /**
//     *
//     */
//    @EqualsAndHashCode.Exclude
//    @OneToMany(targetEntity = UnidadeTipoAvaliacaoDispositivo.class, mappedBy = "unidadeTipoAvaliacao", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<UnidadeTipoAvaliacaoDispositivo> unidadesTiposAvaliacoesDispositivo;

    /**
     *
     */
    private boolean active;

    /**
     *
     */
    @PrePersist
    public void prePersist() {
        this.order = null;
        this.active = this.id == null || this.id == 0 || !this.active;
    }

}
