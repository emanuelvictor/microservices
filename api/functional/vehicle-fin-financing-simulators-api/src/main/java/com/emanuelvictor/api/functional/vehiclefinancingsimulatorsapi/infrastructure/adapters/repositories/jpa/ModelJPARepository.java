package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelJPARepository extends JpaRepository<ModelJPA, String> {

    Optional<ModelJPA> findByNameAndBrand_Name(final String name, final String brandName);
}
