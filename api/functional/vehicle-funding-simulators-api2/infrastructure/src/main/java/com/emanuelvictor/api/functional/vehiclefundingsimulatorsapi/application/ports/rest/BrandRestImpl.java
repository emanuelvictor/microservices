package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.brand.BrandRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.brand.BrandRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.brand.BrandRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class BrandRestImpl extends AbstractRest<Brand, BrandRestInput, BrandRestOutput> implements BrandRest {
}
