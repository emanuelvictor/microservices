package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.brand.BrandRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.brand.BrandRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.brand.BrandRestOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommandOutput;
import org.springframework.stereotype.Service;

@Service
public class BrandRestImpl extends AbstractRest<BrandCommandInput, BrandCommandOutput, BrandRestInput, BrandRestOutput> implements BrandRest {
}
