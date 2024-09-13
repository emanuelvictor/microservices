package com.emanuelvictor.api.functional.address.domain.model.assessment;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class AssessmentType extends AbstractEntity {

    /**
     *
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     *
     */
    @Column(nullable = false)
    private String wording;

    /**
     *
     */
    @Column(nullable = false)
    private String selection;

    /**
     *
     */
    private String one;

    /**
     *
     */
    private String two;

    /**
     *
     */
    private String three;

    /**
     *
     */
    private String four;

    /**
     *
     */
    private String five;

//    /**
//     *
//     */
//    @EqualsAndHashCode.Exclude
//    @OneToMany(targetEntity = UnidadeTipoAvaliacao.class, mappedBy = "tipoAvaliacao", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private Set<UnidadeTipoAvaliacao> unidadesTiposAvaliacoes;

    /**
     * @param id        Long
     * @param name      String
     * @param wording   String
     * @param selection String
     */
    public AssessmentType(final Long id, final String name, final String wording, final String selection) {
        super(id);
        this.name = name;
        this.wording = wording;
        this.selection = selection;
    }
}
