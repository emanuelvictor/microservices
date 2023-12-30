package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "brand")
@Audited(withModifiedFlag = true)
public class BrandJPA {

    @Id
    private String name;

    public BrandJPA(final String name) {
        this.name = name;
    }

    public BrandJPA() {
    }

    public String getName() {
        return name;
    }
}
