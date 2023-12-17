package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;

@Entity
@Audited(withModifiedFlag = true)
public class VehicleJPA {

    @Id
    @Size(max = 6)
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
