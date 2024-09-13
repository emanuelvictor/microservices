package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid;

@FunctionalInterface
public interface UpdateCommand<InputObject, OutputObject> {
    OutputObject execute(InputObject input);
}
