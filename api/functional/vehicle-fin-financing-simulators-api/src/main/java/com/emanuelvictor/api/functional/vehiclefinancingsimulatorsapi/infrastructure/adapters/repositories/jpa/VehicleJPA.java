package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.envers.Audited;

@Entity
@Audited(withModifiedFlag = true)
//@Table(name = "model", uniqueConstraints = { TODO not required
//        @UniqueConstraint(columnNames = {"name", "brand_name"})
//})
public class VehicleJPA {

    @Id
    private String plateNumber;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private ModelJPA model;

    public VehicleJPA() {
    }

    public VehicleJPA(String plateNumber, ModelJPA model) {
        this.plateNumber = plateNumber;
        this.model = model;
    }

    public String getBrandName() {
        return this.model.getBrandName();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getModelName() {
        return model.getName();
    }
}
