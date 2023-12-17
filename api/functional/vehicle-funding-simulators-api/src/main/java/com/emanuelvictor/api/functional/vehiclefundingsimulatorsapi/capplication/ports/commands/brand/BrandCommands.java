package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.brands.post','root.vehicle-funding-simulators.brands','root.vehicle-funding-simulators','root')")
public interface BrandCommands extends Commands<BrandInput, BrandOutput> {

    @Override
    @PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.brands.delete','root.vehicle-funding-simulators.brands','root.vehicle-funding-simulators','root')")
    void delete(BrandInput input);
}