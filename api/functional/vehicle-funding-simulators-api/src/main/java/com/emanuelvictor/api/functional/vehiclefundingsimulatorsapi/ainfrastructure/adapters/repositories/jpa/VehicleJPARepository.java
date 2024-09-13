package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.repositories.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleJPARepository extends JpaRepository<VehicleJPA, String> {

    /**
     * @param filters  {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Page<VehicleJPA>}
     */
    @Query("FROM VehicleJPA vehicle WHERE " +
            "(" +
            "   filter(:filters, vehicle.plateNumber, vehicle.model.name, vehicle.model.brand.name) = true" +
            ")")
    Page<VehicleJPA> getAPageOfVehiclesFromFilters(@Param("filters") String filters, Pageable pageable);

}
