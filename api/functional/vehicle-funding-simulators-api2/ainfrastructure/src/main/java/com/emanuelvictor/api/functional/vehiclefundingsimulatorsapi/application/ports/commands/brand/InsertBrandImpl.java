package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.AbstractInsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class InsertBrandImpl extends AbstractInsertCommand<Brand, BrandCommandInput, BrandCommandOutput> implements InsertBrand {
}
