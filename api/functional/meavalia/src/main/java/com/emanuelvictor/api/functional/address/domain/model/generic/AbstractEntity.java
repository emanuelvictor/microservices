package com.emanuelvictor.api.functional.address.domain.model.generic;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 *
 */
@Data
@MappedSuperclass
public abstract class AbstractEntity implements IPersistentEntity<Long> {

    @Serial
    private static final long serialVersionUID = -3875941859616104733L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     *
     */
    @NotNull
    @Length(max = 150)
    @Column(nullable = false, updatable = false)
    private String tenant;

    /**
     *
     */
    public AbstractEntity() {
    }

    /**
     * @param id Long
     */
    public AbstractEntity(Long id) {
        this.setId(id);
    }

}
