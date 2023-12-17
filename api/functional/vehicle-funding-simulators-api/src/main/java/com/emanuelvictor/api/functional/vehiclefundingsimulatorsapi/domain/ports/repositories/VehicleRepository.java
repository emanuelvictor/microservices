package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.DeleteRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.UpdateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleRepository extends CreateRepository<Vehicle>, DeleteRepository<Vehicle>, UpdateRepository<Vehicle> {

    /**
     * @param plateNumber {@link String}
     * @return {@link Optional<Vehicle>}
     */
    Optional<Vehicle> findVehicleByPlateNumber(String plateNumber);

    /**
     * @param filters  {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Page<Vehicle>}
     */
    Page<Vehicle> getAPageOfVehiclesFromFilters(String filters, Pageable pageable);

}
