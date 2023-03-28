package com.emanuelvictor.api.functional.address.domain.model.assessment;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import com.emanuelvictor.api.functional.address.domain.model.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@lombok.EqualsAndHashCode(callSuper = true)
public class Assessment extends AbstractEntity {

    /**
     *
     */
    @OneToMany(targetEntity = AssessmentEvaluable.class, mappedBy = "assessment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssessmentEvaluable> assessmentsEvauables;

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "grouper_id")
    private Grouper grouper;

    /**
     *
     */
    @NotNull
    @Column(nullable = false, columnDefinition = "NUMERIC(19,0)")
    private int grade;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    /**
     *
     */
    @Column
    private byte[] photo;

    /**
     *
     */
    @Column
    private String photoPath;

    /**
     *
     */
    @Transient
    private Unit unit;

    /**
     *
     */
    @Transient
    private AssessmentType assessmentType;

    /**
     *
     */
    public Assessment() {
    }

    /**
     * @param id Long
     * @param photoPath String
     * @param grade int
     * @param date LocalDateTime
     * @param grouper {@link Grouper}
     * @param unit {@link Unit}
     * @param assessmentType {@link AssessmentType}
     */
    public Assessment(final long id, final String photoPath, final @NotNull int grade, final @NotNull LocalDateTime date, final Grouper grouper, final Unit unit, final AssessmentType assessmentType) {
        super(id);
        this.grade = grade;
        this.date = date;
        this.photoPath = photoPath;
        this.unit = unit;
        this.assessmentType = assessmentType;
        this.grouper = grouper;
    }

    /**
     *
     */
    @PrePersist
    public void prePersist() {

        if (this.photo != null)
            this.photoPath = "./assessments/" + id + "/photo";
        else
            this.photoPath = null;

        this.date = LocalDateTime.now();
    }

}
