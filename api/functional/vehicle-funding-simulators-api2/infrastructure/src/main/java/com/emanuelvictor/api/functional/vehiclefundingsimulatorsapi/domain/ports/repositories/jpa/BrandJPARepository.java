package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandJPARepository extends JpaRepository<BrandJPA, String> {
    
    /**
     * @param filters  {@link String}
     * @param pageable {@link Pageable}
     * @return {@link Page <BrandJPA>}
     */
    @Query("FROM BrandJPA brand WHERE " +
            "(" +
            "   filter(:filters, brand.name) = true" +
            ")")
    Page<BrandJPA> getAPageOfBrandsByFilters(@Param("filters") String filters, Pageable pageable);
}
