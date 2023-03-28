//package com.emanuelvictor.api.functional.address.domain.model.address;
//
//import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import org.hibernate.validator.constraints.Length;
//
//
///**
// * Created by Emanuel Victor on 15/03/2017.
// */
//@Data
//@Entity
//@Table(uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"name", "state_id"})
//})
//@EqualsAndHashCode(callSuper = true)
//public class City extends AbstractEntity {
//
//    /**
//     *
//     */
//    @NotEmpty
//    @Length(max = 200)
//    @Column(nullable = false, length = 200)
//    private String name;
//
//    /**
//     *
//     */
//    @NotNull
//    @ManyToOne(optional = false)
//    private State state;
//
//    /**
//     * @param id Long
//     */
//    public City(final Long id) {
//        this.id = id;
//    }
//
//    /**
//     *
//     */
//    public City() {
//    }
//}
