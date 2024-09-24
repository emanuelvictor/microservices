package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.brand.getpagefromfilters;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.aid.GetPage;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.brand.BrandQueryResultDTO;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.BrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GetPageOfBrandsByFilter implements GetPage<BrandQueryResultDTO, PageBrandFilter> {

    private final BrandRepository brandRepository;

    public GetPageOfBrandsByFilter(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Page<BrandQueryResultDTO> execute(PageBrandFilter filter) {
        final Pageable pageable = Pageable.ofSize(filter.getSizeOfPage());
        return brandRepository.getAPageOfBrandsByFilters(filter.getDefaultFilter(), pageable)
                .map(brand -> new BrandQueryResultDTO(brand.name()));
    }
}
