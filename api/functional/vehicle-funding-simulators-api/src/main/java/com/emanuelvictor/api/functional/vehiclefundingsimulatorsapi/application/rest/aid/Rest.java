package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rest.aid;

public interface Rest<InputObject, OutputObject> {

    OutputObject create(InputObject inputObject);

    OutputObject update(InputObject inputObject);

    void delete(InputObject inputObject);

}
