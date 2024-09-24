package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.delete;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl.AbstractDeleteCommandImpl;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class DeleteBrandImpl extends AbstractDeleteCommandImpl<Brand, BrandCommandInput> implements DeleteBrand {
}
