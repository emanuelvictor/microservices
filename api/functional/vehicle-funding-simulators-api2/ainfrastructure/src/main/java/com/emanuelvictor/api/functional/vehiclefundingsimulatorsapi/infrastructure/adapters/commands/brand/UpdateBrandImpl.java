package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.aid.AbstractUpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.UpdateBrand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class UpdateBrandImpl extends AbstractUpdateCommand<Brand, BrandCommandInput, BrandCommandOutput> implements UpdateBrand {
}
