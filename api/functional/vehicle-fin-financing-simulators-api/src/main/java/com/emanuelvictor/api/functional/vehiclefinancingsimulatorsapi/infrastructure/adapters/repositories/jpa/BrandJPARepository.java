package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.adapters.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandJPARepository extends JpaRepository<BrandJPA, String> {
}
