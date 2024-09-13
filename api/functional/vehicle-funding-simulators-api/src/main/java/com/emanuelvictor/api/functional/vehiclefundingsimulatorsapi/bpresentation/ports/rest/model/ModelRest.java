package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.model;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.aid.Rest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("brands/{code}/models")
public interface ModelRest extends Rest<ModelRestInput, ModelRestOutput> {

}
