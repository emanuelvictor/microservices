package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.BrandJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.BrandJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BrandRepositoryImpl implements BrandRepository {

    private final BrandJPARepository brandJPARepository;

    public BrandRepositoryImpl(BrandJPARepository brandJPARepository) {
        this.brandJPARepository = brandJPARepository;
    }

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

    @Override
    public Page<Brand> getAPageOfBrandsByFilters(String filter, Pageable pageable) {
        return brandJPARepository.getAPageOfBrandsByFilters(filter, pageable)
                .map(brandJPA -> new Brand(brandJPA.getName()));
    }
}
