package com.emanuelvictor.api.functional.address.domain.model.signature.invoice;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.signature.Coupon;
import com.emanuelvictor.api.functional.address.domain.model.signature.PaymentForm;
import com.emanuelvictor.api.functional.address.domain.model.signature.Signature;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Invoice extends AbstractEntity {

    /**
     *
     */
    @Column(unique = true)
    private String orderId;

    /**
     *
     */
    @Column(unique = true)
    private String paymentId;

    /**
     *
     */
    @NotNull
    @Column(nullable = false, updatable = false)
    private String tenant;

    /**
     *
     */
    @Column(unique = true)
    private String linkBoleto;

    /**
     * Inserida quando a fatura é inserida, refere-se á data de abertura da fatura.
     * Hardcoded pro dia 1 de t-do mês
     */
    @NotNull
    @Column(nullable = false, name = "data_abertura")
    private LocalDate dataAbertura;

    /**
     * Inserida quando a fatura é fechada.
     * Nesse momento também é inserido o pedido.
     * A fatura é sempre fechada um mês após sua abertura, no dia 1 (hardcoded)
     */
    @Column(name = "data_fechamento")
    private LocalDate dataFechamento;

    /**
     * Refere-se á data de vencimento da fatura.
     * Sempre é no dia devencimento da assinatura, dois meses depois da abertura da fatura.
     */
    @NotNull
    @Column(nullable = false, name = "data_vencimento")
    private LocalDate dataVencimento;

    /**
     * Inserida quando a fatura é executada
     */
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "assinatura_id")
    private Signature signature;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name = "cupom_id")
    private Coupon coupon;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentForm paymentForm;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = Item.class, mappedBy = "fatura", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Item> itens;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private int quantidadeMaximaAvaliacoes;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private BigDecimal valorMensal;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private BigDecimal valorAvaliacoesExcedentes;

    /**
     *
     */
    public Invoice() {
    }

    /**
     * @param id
     */
    public Invoice(final long id) {
        super(id);
    }

    /**
     * @param id
     */
    public Invoice(final long id, final @NotNull String tenant) {
        super(id);
        this.tenant = tenant;
    }

    /**
     * @param tenant
     * @param signature
     */
    public Invoice(final @NotNull String tenant, final Coupon coupon, final Signature signature) {
        this.tenant = tenant;
        this.signature = signature;
        this.dataAbertura = LocalDate.now().withDayOfMonth(1);
        this.dataVencimento = LocalDate.now().plusMonths(2).withDayOfMonth(this.signature.getDiaVencimentoFatura());

        if (signature.isCanceled())
            this.status = Status.CANCELLED;
        else
            status = Status.CREATED;

        this.paymentForm = this.signature.getPaymentForm();
        this.valorMensal = this.signature.getPlan().getValorMensal();
        this.valorAvaliacoesExcedentes = this.signature.getPlan().getValorAvaliacoesExcedentes();
        this.quantidadeMaximaAvaliacoes = this.signature.getPlan().getQuantidadeAvaliacoes();

        this.coupon = coupon;
    }

    /**
     * @param tenant
     * @param signature
     * @param itens
     */
    public Invoice(final @NotNull String tenant, final Signature signature, final Coupon coupon, final Set<Item> itens) {
        this.tenant = tenant;
        this.signature = signature;
        this.dataAbertura = LocalDate.now().withDayOfMonth(1);
        this.dataVencimento = LocalDate.now().plusMonths(2).withDayOfMonth(this.signature.getDiaVencimentoFatura());

        if (signature.isCanceled())
            this.status = Status.CANCELLED;
        else
            status = Status.CREATED;

        this.paymentForm = this.signature.getPaymentForm();
        this.valorMensal = this.signature.getPlan().getValorMensal();
        this.valorAvaliacoesExcedentes = this.signature.getPlan().getValorAvaliacoesExcedentes();
        this.quantidadeMaximaAvaliacoes = this.signature.getPlan().getQuantidadeAvaliacoes();

        this.itens = itens;

        this.coupon = coupon;
    }

    /**
     * @return
     */
    public BigDecimal getValor() {
        BigDecimal valor = new BigDecimal(0);
        if (itens != null)
            for (int i = 0; i < itens.size(); i++)
                valor = valor.add(new ArrayList<>(itens).get(i).getPrecoComAcressimo());
        return valor;
    }

    /**
     * @return
     */
    public BigDecimal getValorComDesconto() {
        return this.getValor().add(this.getValorDeDesconto().multiply(new BigDecimal(-1)));
    }

    /**
     * @return
     */
    public BigDecimal getValorDeDesconto() {
        if (this.coupon != null)
            return this.getValor().multiply(this.coupon.getPercentualDesconto().multiply(new BigDecimal(0.01)));
        return BigDecimal.ZERO;
    }

    /**
     * @return
     */
    public String getItensString() {
        if (this.itens != null)
            return this.itens.stream().map(item -> item.getQuiz().getName() + " - " + " R$ " + item.getPrecoComAcressimo()).collect(Collectors.joining(", "));
        return null;
    }

    /**
     * @param status
     */
    public void setStatus(final Status status) {
        this.status = status;
    }

    /**
     * @param status
     */
    public void setStatus(final String status) {
        if (status != null)
            this.status = Status.valueOf(status);
    }

    /**
     * @return int
     */
    public int getQuantidadeMaximaAvaliacoes() {
        if (quantidadeMaximaAvaliacoes == 0)
            quantidadeMaximaAvaliacoes = this.signature.getPlan().getQuantidadeAvaliacoes();
        return quantidadeMaximaAvaliacoes;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValorMensal() {
        if (valorMensal == null)
            this.valorMensal = this.signature.getPlan().getValorMensal();
        return valorMensal;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getValorAvaliacoesExcedentes() {
        if (valorAvaliacoesExcedentes == null)
            this.valorAvaliacoesExcedentes = this.signature.getPlan().getValorAvaliacoesExcedentes();
        return valorAvaliacoesExcedentes;
    }

    /**
     * @return boolean
     */
    public boolean isEmAtraso() {
        if (!status.equals(Status.OVERDUE))
            return (status != Status.AUTHORIZED && status != Status.PAID) && (this.dataPagamento == null) && LocalDate.now().isAfter(this.dataVencimento);
        return true;
    }
}
