package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.bpresentation.ports.rest.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public abstract class AbstractRest<InputObject, OutputObject> implements Rest<InputObject, OutputObject> {

    @Autowired
    private Commands<InputObject, OutputObject> commands;

    @PostMapping
    public OutputObject create(InputObject inputObject) {
        return commands.create(inputObject);
    }

    @PutMapping
    public OutputObject update(InputObject inputObject) {
        return commands.update(inputObject);
    }

    @DeleteMapping
    public void delete(InputObject inputObject) {
        commands.delete(inputObject);
    }

}
