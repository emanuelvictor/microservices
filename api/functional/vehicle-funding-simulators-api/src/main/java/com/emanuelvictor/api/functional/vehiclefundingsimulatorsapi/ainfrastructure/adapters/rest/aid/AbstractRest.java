package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.rest.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.InstanciesConverter;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.aid.Rest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractRest<CommandInputObject, CommandOutputObject, InputObject, OutputObject> implements Rest<InputObject, OutputObject> {

    @Autowired
    private Commands<CommandInputObject, CommandOutputObject> commands;

    @PostMapping
    public OutputObject create(InputObject inputObject) {
        final CommandInputObject commandInputObject = convertInputObjectToCommandInputObject(inputObject);
        final CommandOutputObject commandOutputObject = commands.create(commandInputObject);
        return convertCommandOutputObjectToOutputObject(commandOutputObject);
    }

    @PutMapping
    public OutputObject update(InputObject inputObject) {
        final CommandInputObject commandInputObject = convertInputObjectToCommandInputObject(inputObject);
        final CommandOutputObject commandOutputObject = commands.update(commandInputObject);
        return convertCommandOutputObjectToOutputObject(commandOutputObject);
    }

    @DeleteMapping
    public void delete(InputObject inputObject) {
        final CommandInputObject commandInputObject = convertInputObjectToCommandInputObject(inputObject);
        commands.delete(commandInputObject);
    }

    protected CommandInputObject convertInputObjectToCommandInputObject(InputObject inputObject) {
        return InstanciesConverter.tryConvertInstances(getCommandInputObjectClass(), inputObject);
    }

    protected OutputObject convertCommandOutputObjectToOutputObject(CommandOutputObject outputObject) {
        return InstanciesConverter.tryConvertInstances(getOutputObjectClass(), outputObject);
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
