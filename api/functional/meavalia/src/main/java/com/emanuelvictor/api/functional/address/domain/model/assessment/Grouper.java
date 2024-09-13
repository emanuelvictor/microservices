package com.emanuelvictor.api.functional.address.domain.model.assessment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
public class Grouper extends AbstractEntity implements Serializable {

    /**
     * todo NÃO ROLOU, muitos dados
     */
    @Transient
    @EqualsAndHashCode.Exclude
    private List<Assessment> assessments;

    /**
     *
     */
    @Transient
    private String recap;

    /**
     *
     */
    @Column(length = 300)
    private String feedback;

}
