package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.aid.AbstractCommands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandCommands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle.Brand;
import org.springframework.stereotype.Service;

@Service
public class BrandCommandsImpl extends AbstractCommands<Brand, BrandInput, BrandOutput> implements BrandCommands {
}
