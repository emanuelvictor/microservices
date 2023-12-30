package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.DeleteCommand;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.brands.delete','root.vehicle-funding-simulators.brands','root.vehicle-funding-simulators','root')")
public interface DeleteBrand extends DeleteCommand<BrandCommandInput> {

}