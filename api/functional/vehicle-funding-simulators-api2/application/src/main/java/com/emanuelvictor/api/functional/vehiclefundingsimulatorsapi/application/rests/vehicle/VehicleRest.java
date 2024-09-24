package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid.Rest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brands/{brandId}/models/{modelId}/vehicles")
public interface VehicleRest extends Rest<VehicleRestInput, VehicleRestOutput> {

}
