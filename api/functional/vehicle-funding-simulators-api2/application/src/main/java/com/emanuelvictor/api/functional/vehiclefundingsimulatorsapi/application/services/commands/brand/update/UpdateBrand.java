package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.update;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.UpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandOutput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.brands.put','root.vehicle-funding-simulators.brands','root.vehicle-funding-simulators','root')")
public interface UpdateBrand extends UpdateCommand<BrandCommandInput, BrandCommandOutput> {

}