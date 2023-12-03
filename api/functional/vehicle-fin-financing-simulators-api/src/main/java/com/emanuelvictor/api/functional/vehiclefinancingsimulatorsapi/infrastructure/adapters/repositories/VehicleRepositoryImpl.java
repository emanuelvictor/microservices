package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.repositories.VehicleRepository;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleRepositoryImpl implements VehicleRepository {

    @Autowired
    private BrandJPARepository brandJPARepository;

    @Autowired
    private ModelJPARepository modelJPARepository;

    @Autowired
    private VehicleJPARepository vehicleJPARepository;

    @Override
    public Brand save(Brand brand) {
        final BrandJPA brandJPA = new BrandJPA(brand.name());
        brandJPARepository.save(brandJPA);
        return brand;
    }

    @Override
    public Model save(Model model) {
        final BrandJPA brandJPA = new BrandJPA(model.getBrandName());
        final ModelJPA modelJPA = new ModelJPA(model.name(), brandJPA);
        modelJPARepository.save(modelJPA);
        return model;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        final BrandJPA brandJPA = new BrandJPA(vehicle.getBrandName());
        final ModelJPA modelJPA = new ModelJPA(vehicle.getModelName(), brandJPA);
        final VehicleJPA vehicleJPA = new VehicleJPA(vehicle.plateNumber(), modelJPA);
        vehicleJPARepository.save(vehicleJPA);
        return vehicle;
    }

    @Override
    public Optional<Brand> findBrandByName(String name) {
        return brandJPARepository.findById(name)
                .map(brandJPA -> new Brand(brandJPA.getName()));
    }

    @Override
    public Optional<Model> findModelByNameAndBrandName(String name, String brandName) {
        return modelJPARepository.findByNameAndBrand_Name(name, brandName)
                .map(modelJPA -> {
                    final Brand brand = new Brand(modelJPA.getBrandName());
                    return new Model(modelJPA.getName(), brand);
                });
    }

    @Override
    public Optional<Vehicle> findVehicleByPlateNumber(String plateNumber) {
        return vehicleJPARepository.findById(plateNumber)
                .map(vehicleJPA -> {
                    final Brand brand = new Brand(vehicleJPA.getBrandName());
                    final var model = new Model(vehicleJPA.getModelName(), brand);
                    return new Vehicle(vehicleJPA.getPlateNumber(), model);
                });
    }

    /**
     * @param filters  {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Page<Vehicle>}
     */
    @Override
    public Page<Vehicle> getAPageOfVehiclesFromFilters(String filters, Pageable pageable) {
        return vehicleJPARepository.getAPageOfVehiclesFromFilters(filters, pageable)
                .map(vehicleJPA -> {
                    final Brand brand = new Brand(vehicleJPA.getBrandName());
                    final var model = new Model(vehicleJPA.getModelName(), brand);
                    return new Vehicle(vehicleJPA.getPlateNumber(), model);
                });
    }
}
