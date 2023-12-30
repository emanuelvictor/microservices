package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.aid.AbstractRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.brand.BrandRest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.brand.BrandRestInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.presentation.ports.rest.brand.BrandRestOutput;
import org.springframework.stereotype.Service;

@Service
public class BrandRestImpl extends AbstractRest<BrandCommandInput, BrandCommandOutput, BrandRestInput, BrandRestOutput> implements BrandRest {
}
