package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.AbstractInsertApplicationService;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.insert.InsertBrandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.insert.InsertBrandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("brands")
public class BrandRest {

    private final AbstractInsertApplicationService<Brand, InsertBrandInput, InsertBrandOutput> insertBrandCommandService;

    @PostMapping
    InsertBrandOutput insertBrand(InsertBrandInput insertBrandInput) {
        return insertBrandCommandService.execute(insertBrandInput);
    }
}
