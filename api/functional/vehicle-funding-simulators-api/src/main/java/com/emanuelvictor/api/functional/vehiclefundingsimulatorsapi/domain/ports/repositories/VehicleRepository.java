package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Vehicle;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.InsertRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public abstract class VehicleRepository implements InsertRepository<Vehicle> {

    /**
     * @param plateNumber {@link String}
     * @return {@link Optional<Vehicle>}
     */
    public abstract Optional<Vehicle> findVehicleByPlateNumber(String plateNumber);

    /**
     * @param filters  {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Page<Vehicle>}
     */
    public abstract Page<Vehicle> getAPageOfVehiclesFromFilters(String filters, Pageable pageable);

}
