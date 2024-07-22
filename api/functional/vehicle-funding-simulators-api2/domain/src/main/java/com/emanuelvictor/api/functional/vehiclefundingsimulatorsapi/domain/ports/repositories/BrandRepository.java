package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;


import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BrandRepository extends CreateRepository<Brand> {

    Optional<Brand> findBrandByName(String name);

    Page<Brand> getAPageOfBrandsByFilters(String filter, Pageable pageable);

}
