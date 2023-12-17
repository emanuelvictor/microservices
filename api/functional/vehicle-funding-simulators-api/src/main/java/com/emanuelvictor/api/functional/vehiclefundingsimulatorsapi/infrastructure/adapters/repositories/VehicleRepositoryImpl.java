package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.VehicleRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.repositories.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleRepositoryImpl extends VehicleRepository {

    @Autowired
    private VehicleJPARepository vehicleJPARepository;

    @Override
    public Vehicle create(Vehicle vehicle) {
        final BrandJPA brandJPA = new BrandJPA(vehicle.getBrandName());
        final ModelJPA modelJPA = new ModelJPA(vehicle.getModelName(), brandJPA);
        final VehicleJPA vehicleJPA = new VehicleJPA(vehicle.plateNumber(), modelJPA);
        vehicleJPARepository.save(vehicleJPA);
        return vehicle;
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
