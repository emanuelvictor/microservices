package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.Converter;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.DeleteCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.DeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDeleteCommandImpl<DomainObject, InputObject> implements DeleteCommand<InputObject> {


    @Autowired(required = false)
    private DeleteRepository<DomainObject> deleteRepository;

    @Autowired
    private Converter<DomainObject, InputObject, ?> converter;

    @Override
    public void execute(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        deleteRepository.delete(domainObject);
    }

    private DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return converter.convertInputObjectToDomainObject(inputObject);
    }
}
