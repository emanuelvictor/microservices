package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl.AbstractUpdateCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class UpdateBrandImpl extends AbstractUpdateCommandImpl<Brand, BrandCommandInput, BrandCommandOutput> implements UpdateBrand {
}
