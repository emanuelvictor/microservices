package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.brand.getbrandbyname;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.aid.GetOne;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.brand.BrandQueryResultDTO;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.BrandRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetBrandByName implements GetOne<BrandQueryResultDTO, OneBrandFilter> {

    private final BrandRepository brandRepository;

    public GetBrandByName(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Optional<BrandQueryResultDTO> execute(OneBrandFilter oneBrandFilter) {
        return brandRepository.findBrandByName(oneBrandFilter.getName())
                .map(brand -> new BrandQueryResultDTO(brand.name()));
    }
}
