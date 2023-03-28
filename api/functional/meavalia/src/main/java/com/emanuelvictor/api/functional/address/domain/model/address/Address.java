package com.emanuelvictor.api.functional.address.domain.model.address;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Emanuel Victor on 15/03/2017.
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Address extends AbstractEntity {

    /**
     *
     */
    @Column(length = 200)
    private String street;

    /**
     *
     */
    @Column(length = 200)
    private String complments;

    /**
     *
     */
    @Column(length = 200)
    private String neighborhood;

    /**
     * TODO no have translate.
     */
    @Column
    private String cep;

    /**
     *
     */
    @Column(length = 20)
    private String number;

    /**
     *
     */
    @Column
    private String cityName;

    /**
     *
     */
    @Column
    private String stateName;

    /**
     *
     */
    @Column
    private String stateAbbreviation;

    /**
     *
     */
    @Column
    private String countryName;
}
