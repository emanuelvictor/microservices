package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.AbstractCommands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.groups.post','root.vehicle-funding-simulators.groups','root.vehicle-funding-simulators','root')")
public class BrandCommands extends AbstractCommands<Brand, BrandInput, BrandOutput> {

    @Override
    @PreAuthorize("hasAnyAuthority('root.vehicle-funding-simulators.groups.delete','root.vehicle-funding-simulators.groups','root.vehicle-funding-simulators','root')")
    public void delete(BrandInput input) {
        super.create(input);
    }
}