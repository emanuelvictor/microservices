package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.BrandJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.ModelJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.VehicleJPA;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa.VehicleJPARepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehicleRepositoryImpl implements VehicleRepository {

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

    @Override
    public void delete(Vehicle vehicle) {
        throw new NotImplementedException("Not implemented yet");
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        throw new NotImplementedException("Not implemented yet");
    }
}
