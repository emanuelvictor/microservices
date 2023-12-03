package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Brand;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Model;
import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleRepository {

    Brand save(final Brand brand);

    Model save(final Model model);

    Vehicle save(final Vehicle vehicle);

    Optional<Brand> findBrandByName(String name);

    Optional<Model> findModelByNameAndBrandName(String name, String brandName);

    Optional<Vehicle> findVehicleByPlateNumber(String plateNumber);

    Page<Vehicle> getAPageOfVehiclesFromFilters(String filters, Pageable pageable);
}
