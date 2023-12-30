package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid;

@FunctionalInterface
public interface InsertCommand<InputObject, OutputObject> {
    OutputObject execute(InputObject input);
}
