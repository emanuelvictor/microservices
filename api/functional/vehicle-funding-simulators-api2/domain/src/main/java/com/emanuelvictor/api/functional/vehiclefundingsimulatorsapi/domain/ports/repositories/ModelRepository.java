package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;


import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;

import java.util.Optional;

public interface ModelRepository extends CreateRepository<Model> {

    Optional<Model> findModelByNameAndBrandName(String name, String brandName);


}
