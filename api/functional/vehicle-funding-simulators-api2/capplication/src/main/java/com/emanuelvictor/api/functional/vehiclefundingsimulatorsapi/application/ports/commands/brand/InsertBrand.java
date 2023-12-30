package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid.InsertCommand;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.brands.post','root.vehicle-funding-simulators.brands','root.vehicle-funding-simulators','root')")
public interface InsertBrand extends InsertCommand<BrandCommandInput, BrandCommandOutput> {

}