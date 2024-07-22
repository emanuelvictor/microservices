package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid;

@FunctionalInterface
public interface InsertCommand<InputObject, OutputObject> {
    OutputObject execute(InputObject input);
}
