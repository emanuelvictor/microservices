package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rest;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandInput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.brand.BrandOutput;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rest.aid.AbstractRest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("brands")
public class BrandRest extends AbstractRest<BrandInput, BrandOutput> {


}
