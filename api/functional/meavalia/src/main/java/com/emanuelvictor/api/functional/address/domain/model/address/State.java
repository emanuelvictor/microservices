//package com.emanuelvictor.api.functional.address.domain.model.address;
//
//
//import com.emanuelvictor.api.functional.address.domain.model.generic.AbstractEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//
///**
// * Created by Emanuel Victor on 15/03/2017.
// */
//@Data
//@Entity
//@EqualsAndHashCode(callSuper = true)
//@Table(uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"name", "country_id"})
//})
//public class State extends AbstractEntity {
//
//    /**
//     *
//     */
//    @NotEmpty
//    @Column(nullable = false, length = 200)
//    private String name;
//
//    /**
//     *
//     */
//    @NotEmpty
//    @Column(nullable = false, length = 2)
//    private String abbreviation;
//
//    /**
//     *
//     */
//    @NotNull
//    @ManyToOne(optional = false)
//    private Country country;
//
//    /**
//     *
//     */
//    public State() {
//    }
//
//    /**
//     * @param id Long
//     */
//    public State(final Long id) {
//        super(id);
//    }
//}
