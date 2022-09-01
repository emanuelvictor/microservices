package com.emanuelvictor.api.functional.assessment.domain.entities;

import com.emanuelvictor.api.functional.assessment.domain.entities.generic.PersistentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 25/08/2021
 */
@Data
@Entity
@Audited
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Unity extends PersistentEntity {

    /**
     *
     */
    @Column(nullable = false, length = 150, unique = true)
    private String name;

    /**
     * @param name String
     */
    public Unity(final String name) {
        this.name = name;
    }
}
