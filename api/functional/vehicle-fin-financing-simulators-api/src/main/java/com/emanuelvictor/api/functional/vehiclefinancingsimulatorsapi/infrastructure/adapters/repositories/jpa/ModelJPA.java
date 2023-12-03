package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import org.hibernate.envers.Audited;

@Entity
@Audited(withModifiedFlag = true)
//@Table(name = "model", uniqueConstraints = { TODO required
//        @UniqueConstraint(columnNames = {"name", "brand_name"})
//})
public class ModelJPA {

    //    TODO cannot be the id, names of models can be repeated.
    @Id
    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private BrandJPA brand;

    public ModelJPA() {
    }

    public ModelJPA(String name, BrandJPA brand) {
        this.name = name;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public String getBrandName() {
        return brand.getName();
    }
}
