package com.emanuelvictor.api.functional.address.domain.model.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
        @UniqueConstraint(columnNames = {"name", "state_id"})
})
public class City {

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
    @Length(max = 200)
    @Column(nullable = false, length = 200)
    private String name;

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    private State state;

    /**
     * @param id Long
     */
    public City(final Long id) {
        this.id = id;
    }

    /**
     * @param name  String
     * @param state {@link State}
     */
    public City(final String name, final State state) {
        this.name = name;
        this.state = state;
    }
}

