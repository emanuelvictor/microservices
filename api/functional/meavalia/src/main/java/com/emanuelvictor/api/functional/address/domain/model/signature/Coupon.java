package com.emanuelvictor.api.functional.address.domain.model.signature;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Coupon extends AbstractEntity implements Serializable {

    /**
     *
     */
    @Length(max = 150)
    @Column(unique = true)
    private String tenant;

    /**
     *
     */
    @NotNull
    @Length(max = 150)
    @Column(nullable = false)
    private String codigo;

    /**
     *
     */
    @NotNull
    @Column(nullable = false, name = "percentual_desconto")
    private BigDecimal percentualDesconto;

    /**
     *
     */
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    /**
     *
     */
    public Coupon() {
    }

    /**
     * @param id
     */
    public Coupon(final long id) {
        super(id);
    }

    /**
     * @param id
     * @param codigo
     */
    public Coupon(final Long id, final @NotNull @Length(max = 150) String codigo) {
        super(id);
        this.codigo = codigo;
    }
}
