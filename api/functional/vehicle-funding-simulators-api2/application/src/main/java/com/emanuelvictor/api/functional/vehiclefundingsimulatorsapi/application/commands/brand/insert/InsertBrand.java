package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.insert;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.InsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.brands.post','root.vehicle-funding-simulators.brands','root.vehicle-funding-simulators','root')")
public interface InsertBrand extends InsertCommand<BrandCommandInput, BrandCommandOutput> {

}