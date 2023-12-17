package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid;

public interface Commands<Input, Output> {

    Output create(Input input);

    Output update(Input input);

    void delete(Input input);

}
