package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.brand.BrandRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.brand.BrandRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.brand.BrandRestOutput;
import org.springframework.stereotype.Service;

@Service
public class BrandRestImpl extends AbstractRest<BrandCommandInput, BrandCommandOutput, BrandRestInput, BrandRestOutput> implements BrandRest {
}
