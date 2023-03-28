package com.emanuelvictor.api.functional.address.domain.model.signature;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"quantidade_avaliacoes", "valor_avaliacoes_excedentes", "valor_mensal"})
})
@EqualsAndHashCode(callSuper = true)
public class Plan extends AbstractEntity implements Serializable {

    /**
     *
     */
    @Column(nullable = false, unique = true)
    private String nome;

    /**
     *
     */
    @Column(nullable = false, name = "quantidade_avaliacoes")
    private int quantidadeAvaliacoes;

    /**
     *
     */
    @Column(nullable = false, name = "valor_avaliacoes_excedentes")
    private BigDecimal valorAvaliacoesExcedentes;

    /**
     *
     */
    @Column(nullable = false, name = "valor_mensal")
    private BigDecimal valorMensal;

    /**
     *
     */
    public Plan() {
    }

    /**
     * @param id
     */
    public Plan(final long id) {
        super(id);
    }
}
