package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands;

@FunctionalInterface
public interface Command<I, O> {

    O execute(I input);
}
