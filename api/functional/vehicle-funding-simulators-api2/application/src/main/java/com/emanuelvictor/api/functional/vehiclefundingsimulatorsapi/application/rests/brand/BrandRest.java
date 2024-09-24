package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid.Rest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brands")
public interface BrandRest extends Rest<BrandRestInput, BrandRestOutput> {

}
