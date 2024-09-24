package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl.AbstractInsertCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class InsertBrandImpl extends AbstractInsertCommandImpl<Brand, BrandCommandInput, BrandCommandOutput> implements InsertBrand {
}
