package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid.Rest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brands/{code}/models/{name}/vehicles")
public interface VehicleRest extends Rest<VehicleRestInput, VehicleRestOutput> {

}
