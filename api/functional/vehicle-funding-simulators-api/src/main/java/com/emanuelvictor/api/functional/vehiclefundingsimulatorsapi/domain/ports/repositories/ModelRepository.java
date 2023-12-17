package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;

import java.util.Optional;

public abstract class ModelRepository implements CreateRepository<Model> {

    public abstract Optional<Model> findModelByNameAndBrandName(String name, String brandName);


}
