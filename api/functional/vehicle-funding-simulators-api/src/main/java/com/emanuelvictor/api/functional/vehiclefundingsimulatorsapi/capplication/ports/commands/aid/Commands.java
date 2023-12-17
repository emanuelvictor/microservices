package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid;

public interface Commands<InputObject, OutputObject> {

    OutputObject create(InputObject input);

    OutputObject update(InputObject input);

    void delete(InputObject input);
}
