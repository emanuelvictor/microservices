package com.emanuelvictor.api.functional.address.domain.model.signature;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
public enum PaymentForm {

    /**
     *
     */
    BOLETO(0),

    /**
     *
     */
    CARTAO(1);

    /**
     *
     */
    public final int formaPagamento;

    /**
     * @param formaPagamento
     */
    PaymentForm(final int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
