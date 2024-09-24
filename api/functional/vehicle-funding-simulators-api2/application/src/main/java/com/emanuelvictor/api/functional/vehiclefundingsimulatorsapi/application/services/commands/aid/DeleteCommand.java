package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid;

@FunctionalInterface
public interface DeleteCommand<InputObject> {
    void execute(InputObject input);
}
