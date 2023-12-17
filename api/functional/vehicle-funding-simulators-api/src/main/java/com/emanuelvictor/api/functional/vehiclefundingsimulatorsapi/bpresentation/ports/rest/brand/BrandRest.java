package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.brand;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.brand.BrandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.aid.AbstractRest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("brands")
public class BrandRest extends AbstractRest<BrandInput, BrandOutput> {

}
