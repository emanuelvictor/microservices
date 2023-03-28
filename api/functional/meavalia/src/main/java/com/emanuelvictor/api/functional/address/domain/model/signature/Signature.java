package com.emanuelvictor.api.functional.address.domain.model.signature;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.emanuelvictor.api.functional.address.domain.model.address.Address;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Signature extends AbstractEntity {

    /**
     *
     */
    @Column
    private String clientId;

    /**
     *
     */
    @Column
    private String mounthOfValidate;

    /**
     *
     */
    @Column
    private String yearValidate;

    /**
     *
     */
    @Min(1)
    @Max(28)
    @NotNull
    @Column(nullable = false)
    private short diaVencimentoFatura = 5;

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
    @Column(unique = true)
    private String numeroCartao;

    /**
     *
     */
    @Column
    private String nomeTitular;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private boolean agruparFaturas = true;

    /**
     *
     */
    @Column
    private Boolean souEmpresa = false;

    /**
     *
     */
    @Column
    private String documentoTitular;

    /**
     *
     */
    @Column
    private LocalDate dataNascimentoTitular;

    /**
     *
     */
    @Column
    private Byte areaCode;

    /**
     *
     */
    @Column
    private Long telephone;

    /**
     * Para pagamento com cartão de crédito
     */
    @Column
    private String hash;

    /**
     *
     */
    @JoinColumn(name = "endereco_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Address.class)
    private Address address;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Plan plan;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private boolean canceled;
//    /**
//     * TODO testar
//     */
//    @EqualsAndHashCode.Exclude
//    @OneToMany(targetEntity = Dispositivo.class, mappedBy = "assinatura", fetch = FetchType.EAGER, orphanRemoval = true)
//    private Set<Dispositivo> dispositivos;

    /**
     * @return boolean
     */
    @Transient
    public boolean isCompleted() {

        if (this.address == null)
            return false;

        if (this.address.getStreet() == null)
            return false;
        if (this.address.getNeighborhood() == null)
            return false;
        if (this.address.getCep() == null)
            return false;
        if (this.address.getNumber() == null)
            return false;

        if (this.address.getCity() == null)
            return false;
        if (this.address.getCity().getName() == null)
            return false;

        if (this.address.getCity().getState() == null)
            return false;
        if (this.address.getCity().getState().getName() == null)
            return false;
        if (this.address.getCity().getState().getAbbreviation() == null)
            return false;

        if (this.address.getCity().getState().getCountry() == null)
            return false;
        if (this.address.getCity().getState().getCountry().getName() == null)
            return false;

        if (this.areaCode == null)
            return false;
        if (this.telephone == null)
            return false;

        if (this.plan == null)
            return false;

        if (this.paymentForm == null)
            return false;

        if (this.paymentForm.equals(PaymentForm.CARTAO)) {
            if (this.hash == null)
                return false;
            if (this.documentoTitular == null)
                return false;
            if (this.dataNascimentoTitular == null)
                return false;
            if (this.numeroCartao == null)
                return false;
            if (this.nomeTitular == null)
                return false;
            return this.yearValidate != null;
        }

        return true;
    }

    /**
     * @return boolean
     */
    @Transient
    public boolean isTransactioned() {
        return isCompleted() && clientId != null;
    }

}
