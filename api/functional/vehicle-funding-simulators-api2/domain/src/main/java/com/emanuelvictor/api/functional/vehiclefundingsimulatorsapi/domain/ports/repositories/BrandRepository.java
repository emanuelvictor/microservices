package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;


import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;

import java.util.Optional;

public interface BrandRepository extends CreateRepository<Brand> {

    Optional<Brand> findBrandByName(String name);

}
