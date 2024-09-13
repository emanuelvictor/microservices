package com.emanuelvictor.api.functional.address.domain.model.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


/**
 * Created by Emanuel Victor on 15/03/2017.
 */
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    @NotEmpty
    @Column(nullable = false, length = 200)
    private String name;

    /**
     * @param name String
     */
    public Country(final String name) {
        this.name = name;
    }

}
