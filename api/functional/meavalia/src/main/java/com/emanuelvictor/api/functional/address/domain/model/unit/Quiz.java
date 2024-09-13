package com.emanuelvictor.api.functional.address.domain.model.unit;

import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@lombok.EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tenant", "name"})
})
public class Quiz extends AbstractEntity {

    /**
     *
     */
    @Column
    private LocalDate deactivationDate;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private LocalDateTime expirationCodeDate;

    /**
     *
     */
    @Column(unique = true)
    private String serialNumber;

    /**
     *
     */
    @NotNull
    @Max(999999)
    @Min(100000)
    @Column(nullable = false, unique = true)
    private long code;

    /**
     *
     */
    @NotNull
    @Max(999999)
    @Min(100000)
    @Column(length = 6, nullable = false)
    private Long password;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private String name;

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    @Min(value = 5, message = "O mínimo são 5 segundos") // TODO
    @Max(value = 600, message = "O máximo são 10 minutos (600 segundos)") // TODO
    private short time = 30;

    /**
     *
     */
    private boolean brokenLineOnSelectionOfEvaluableItem;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(targetEntity = UnitAssessmentTypeQuiz.class, mappedBy = "device", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<UnitAssessmentTypeQuiz> unitsTypesAssessmentsDevice;

    /**
     * @param id                                   Long
     * @param deactivationDate                     LocalDate
     * @param expirationCodeDate                   LocalDateTime
     * @param serialNumber                         String
     * @param code                                 long
     * @param password                             Long
     * @param name                                 String
     * @param time                                 short
     * @param brokenLineOnSelectionOfEvaluableItem boolean
     */
    public Quiz(final Long id, final LocalDate deactivationDate,
                final @NotNull LocalDateTime expirationCodeDate,
                final String serialNumber,
                final @NotNull @Max(999999) @Min(100000) long code,
                final @NotNull @Max(999999) @Min(100000) Long password, final @NotNull String name,
                final @NotNull @Min(value = 5, message = "O mínimo são 5 segundos") @Max(value = 600, message = "O máximo são 10 minutos (600 segundos)") short time,
                final boolean brokenLineOnSelectionOfEvaluableItem) {
        super(id);
        this.deactivationDate = deactivationDate;
        this.expirationCodeDate = expirationCodeDate;
        this.serialNumber = serialNumber;
        this.code = code;
        this.password = password;
        this.name = name;
        this.time = time;
        this.brokenLineOnSelectionOfEvaluableItem = brokenLineOnSelectionOfEvaluableItem;
    }

    /**
     * @return String
     */
    public static long getRandomNumberInRange() {
        final Random r = new Random();
        return r.nextInt((999999 - 100000) + 1) + 100000;
    }

    /**
     * @return Set<Unit>
     */
    public Set<Unit> getUnits() {
        return this.unitsTypesAssessmentsDevice != null ? this.unitsTypesAssessmentsDevice.stream().map(unitAssessmentTypeQuiz -> unitAssessmentTypeQuiz.getUnitAssessmentType().getUnit()).collect(Collectors.toSet()) : null;
    }

    /**
     * TODO falcatrua
     *
     * @return Set<Unidade>
     */
    public void setUnits(final Set<Unit> units) {

    }

    //    /**
//     * (non-Javadoc)
//     *
//     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
//     */
//    @Override
    @Transient
    public String getUsername() {
        return String.valueOf(this.code);
    }

//    /**
//     *
//     */
//    @Override
//    @Transient
//    @JsonIgnore
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        final Set<Perfil> authorities = new HashSet<>();
//
//        authorities.add(Perfil.DISPOSITIVO);
//
//        return authorities;
//    }

    /**
     *
     */
//    @Override
    public boolean isEnabled() {
        return this.deactivationDate == null || LocalDate.now().isBefore(this.deactivationDate);
    }

    /**
     * @return
     */
//    @Override
    public String getPassword() {
        return String.valueOf(this.password);
    }

    /**
     * @return
     */
//    @Override
    public boolean isAccountNonExpired() {
        return !expirationCodeDate.isBefore(LocalDateTime.now().minusHours(1)); //TODO arrumar data da aplicação;
    }

    /**
     * @return
     */
//    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return
     */
//    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *
     */
    @PreUpdate
    @PrePersist
    public void prePersistAndUpdate() {
        this.code = getRandomNumberInRange();
        this.expirationCodeDate = LocalDateTime.now().plusHours(1);

        if (password == null)
            password = getRandomNumberInRange();
    }

    /**
     * @return LocalDate
     */
    public LocalDate getReactivationDate() {
        if (this.deactivationDate == null)
            return null;
        return this.deactivationDate.plusMonths(6);
    }

    /**
     * @return int
     */
    public int getTimeInMilis() {
        return time * 1000;
    }
}
