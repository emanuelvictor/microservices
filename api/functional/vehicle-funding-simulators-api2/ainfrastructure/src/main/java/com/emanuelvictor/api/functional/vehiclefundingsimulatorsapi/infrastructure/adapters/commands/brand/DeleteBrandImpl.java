package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.adapters.commands.aid.AbstractDeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand.DeleteBrand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class DeleteBrandImpl extends AbstractDeleteCommand<Brand, BrandCommandInput> implements DeleteBrand {
}
