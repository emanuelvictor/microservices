package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl.AbstractUpdateCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class UpdateBrandImpl extends AbstractUpdateCommandImpl<Brand, BrandCommandInput, BrandCommandOutput> implements UpdateBrand {
}
