package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.delete;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl.AbstractDeleteCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class DeleteBrandImpl extends AbstractDeleteCommandImpl<Brand, BrandCommandInput> implements DeleteBrand {
}
