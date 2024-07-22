package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl.AbstractInsertCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class InsertBrandImpl extends AbstractInsertCommandImpl<Brand, BrandCommandInput, BrandCommandOutput> implements InsertBrand {
}
