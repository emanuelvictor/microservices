package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid;

@FunctionalInterface
public interface InsertCommand<InputObject, OutputObject> {
    OutputObject execute(InputObject input);
}
