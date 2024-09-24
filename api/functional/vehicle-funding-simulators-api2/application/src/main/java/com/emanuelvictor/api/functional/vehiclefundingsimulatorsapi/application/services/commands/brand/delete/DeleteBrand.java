package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.delete;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.DeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.brand.BrandCommandInput;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.brands.delete','root.vehicle-funding-simulators.brands','root.vehicle-funding-simulators','root')")
public interface DeleteBrand extends DeleteCommand<BrandCommandInput> {

}