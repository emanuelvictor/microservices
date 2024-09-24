package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.aid.converters.InstanciesConverterWithGson;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.DeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.InsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.UpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractRest<CommandInputObject, CommandOutputObject, InputObject, OutputObject> implements Rest<InputObject, OutputObject> {

    @Autowired(required = false)
    private InsertCommand<CommandInputObject, CommandOutputObject> insertCommand;

    @Autowired(required = false)
    private UpdateCommand<CommandInputObject, CommandOutputObject> updateCommand;

    @Autowired(required = false)
    private DeleteCommand<CommandInputObject> deleteCommand;

    // TODO if the command is not implemented, then throw a exception
    @PostMapping
    public OutputObject create(InputObject inputObject) {
        final CommandInputObject commandInputObject = convertInputObjectToCommandInputObject(inputObject);
        final CommandOutputObject commandOutputObject = insertCommand.execute(commandInputObject);
        return convertCommandOutputObjectToOutputObject(commandOutputObject);
    }

    @PutMapping
    public OutputObject update(InputObject inputObject) {
        final CommandInputObject commandInputObject = convertInputObjectToCommandInputObject(inputObject);
        final CommandOutputObject commandOutputObject = updateCommand.execute(commandInputObject);
        return convertCommandOutputObjectToOutputObject(commandOutputObject);
    }

    @DeleteMapping
    public void delete(InputObject inputObject) {
        final CommandInputObject commandInputObject = convertInputObjectToCommandInputObject(inputObject);
        deleteCommand.execute(commandInputObject);
    }

    protected CommandInputObject convertInputObjectToCommandInputObject(InputObject inputObject) {
        return InstanciesConverterWithGson.tryConvertInstances(getCommandInputObjectClass(), inputObject);
    }

    protected OutputObject convertCommandOutputObjectToOutputObject(CommandOutputObject outputObject) {
        return InstanciesConverterWithGson.tryConvertInstances(getOutputObjectClass(), outputObject);
    }

    private Class<CommandInputObject> getCommandInputObjectClass() {
        return reflectClassType(0);
    }

    private Class<OutputObject> getOutputObjectClass() {
        return reflectClassType(3);
    }

    @SuppressWarnings("unchecked")
    private Class reflectClassType(int indexOfGenericClass) {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[indexOfGenericClass]);
    }

}
