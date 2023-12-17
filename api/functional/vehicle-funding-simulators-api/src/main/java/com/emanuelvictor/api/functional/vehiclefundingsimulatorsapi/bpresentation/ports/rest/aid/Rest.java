package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.aid;

public interface Rest<InputObject, OutputObject> {

    OutputObject create(InputObject inputObject);

    OutputObject update(InputObject inputObject);

    void delete(InputObject inputObject);

}
