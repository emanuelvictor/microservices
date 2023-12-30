package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.BrandJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.ModelJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.ModelJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ModelRepositoryImpl implements ModelRepository {

    @Autowired
    private ModelJPARepository modelJPARepository;

    @Override
    public Optional<Model> findModelByNameAndBrandName(String name, String brandName) {
        return modelJPARepository.findByNameAndBrand_Name(name, brandName)
                .map(modelJPA -> {
                    final Brand brand = new Brand(modelJPA.getBrandName());
                    return new Model(modelJPA.getName(), brand);
                });
    }

    @Override
    public Model create(Model model) {
        final BrandJPA brandJPA = new BrandJPA(model.getBrandName());
        final ModelJPA modelJPA = new ModelJPA(model.name(), brandJPA);
        modelJPARepository.save(modelJPA);
        return model;
    }

}
