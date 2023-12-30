package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid;

@FunctionalInterface
public interface DeleteCommand<InputObject> {
    void execute(InputObject input);
}
