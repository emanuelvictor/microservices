package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.BrandJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa.BrandJPARepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BrandRepositoryImpl implements BrandRepository {

    @Autowired
    private BrandJPARepository brandJPARepository;

    @Override
    public Optional<Brand> findBrandByName(String name) {
        return brandJPARepository.findById(name)
                .map(brandJPA -> new Brand(brandJPA.getName()));
    }

    @Override
    public Brand create(Brand brand) {
        final BrandJPA brandJPA = new BrandJPA(brand.name());
        brandJPARepository.save(brandJPA);
        return brand;
    }
}
