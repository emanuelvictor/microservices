package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid;

@FunctionalInterface
public interface UpdateCommand<InputObject, OutputObject> {
    OutputObject execute(InputObject input);
}
