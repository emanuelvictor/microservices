package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid;

public interface Rest<InputObject, OutputObject> {

    OutputObject create(InputObject inputObject);

    OutputObject update(Object id, InputObject inputObject);

    void delete(Object id);

}
