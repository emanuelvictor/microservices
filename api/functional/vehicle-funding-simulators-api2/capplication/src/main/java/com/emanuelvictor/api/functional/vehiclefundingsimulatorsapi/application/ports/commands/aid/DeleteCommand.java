package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid;

@FunctionalInterface
public interface DeleteCommand<InputObject> {
    void execute(InputObject input);
}
