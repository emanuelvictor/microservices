package com.emanuelvictor.api.functional.address.domain.model.address;

import jakarta.persistence.*;
import lombok.*;

/**
 * Created by Emanuel Victor on 15/03/2017.
 */
@Data
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

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
     * {@link City}
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

}
