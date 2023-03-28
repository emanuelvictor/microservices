package com.emanuelvictor.api.functional.address.domain.model.address;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


/**
 * Created by Emanuel Victor on 15/03/2017.
 */
@Data
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "country_id"})
})
public class State {

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
     *
     */
    @NotEmpty
    @Column(nullable = false, length = 2)
    private String abbreviation;

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    private Country country;

    /**
     * @param name         String
     * @param abbreviation String
     * @param country      {@link Country}
     */
    public State(final String name, final String abbreviation, final Country country) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.country = country;
    }
}
