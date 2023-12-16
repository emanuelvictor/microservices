package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.InsertRepository;

import java.util.Optional;

public abstract class BrandRepository implements InsertRepository<Brand>{

    public abstract Optional<Brand> findBrandByName(String name);

}
