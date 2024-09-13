package com.emanuelvictor.api.functional.address.domain.model.signature.invoice;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.unit.Quiz;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"device_id", "invoice_id"})
})
public class Item extends AbstractEntity implements Serializable {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -98765951648456159L;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id")
    private Quiz quiz;

    /**
     *
     */
    @Column
    private Integer avaliacoesExcedentes;

    /**
     * Total permitido
     */
    @Column
    private Integer totalAvaliacoes;

    /**
     *
     */
    public Item() {
    }

    /**
     * @param id Long
     */
    public Item(final Long id) {
        super(id);
    }

    /**
     * @return
     */
    public BigDecimal getPrecoComAcressimo() {
        return this.invoice.getValorMensal().add(this.getValorDeAcressimo());
    }

    /**
     * @return
     */
    public BigDecimal getValorDeAcressimo() {
        final BigDecimal preco;

        if (totalAvaliacoes != null && invoice != null && totalAvaliacoes > invoice.getQuantidadeMaximaAvaliacoes()) {
            avaliacoesExcedentes = totalAvaliacoes - invoice.getQuantidadeMaximaAvaliacoes();
            preco = invoice.getValorMensal().add(invoice.getValorAvaliacoesExcedentes().multiply(new BigDecimal(avaliacoesExcedentes)));
        } else preco = BigDecimal.ZERO;

        return preco;
    }

    /**
     * @param totalAvaliacoes
     */
    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;

        // Calcula preços
        this.getValorDeAcressimo();
    }
}
