package com.emanuelvictor.api.functional.address.domain.repository;

import com.emanuelvictor.api.functional.address.domain.model.address.Address;
import com.emanuelvictor.api.functional.address.domain.model.address.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 *
 */
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * @param nameOfCity            String
     * @param abbreviationFromState String
     * @return {@link Optional<City>}
     */
    @Query("SELECT city FROM City city WHERE (LOWER(city.name) = LOWER(:nameOfCity) AND LOWER(city.state.abbreviation) = LOWER(:abbreviationFromState))")
    Optional<City> listCitiesByNameAndAbbreviationFromState(@Param("nameOfCity") final String nameOfCity, @Param("abbreviationFromState") final String abbreviationFromState);

}
