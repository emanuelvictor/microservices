package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.aid.AbstractInsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.InsertBrand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class InsertBrandImpl extends AbstractInsertCommand<Brand, BrandCommandInput, BrandCommandOutput> implements InsertBrand {
}
