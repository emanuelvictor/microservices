package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.AbstractUpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class UpdateBrandImpl extends AbstractUpdateCommand<Brand, BrandCommandInput, BrandCommandOutput> implements UpdateBrand {
}
