package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.impl;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.Converter;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.commands.aid.InsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractInsertCommandImpl<DomainObject, InputObject, OutputObject> implements InsertCommand<InputObject, OutputObject> {

    @Autowired(required = false) // TODO substituir pelo construtor
    private CreateRepository<DomainObject> createRepository;

    @Autowired
    private Converter<DomainObject, InputObject, OutputObject> converter;

    @Override
    public OutputObject execute(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        return convertDomainObjectoToOutputObject(createRepository.create(domainObject));
    }

    private DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return converter.convertInputObjectToDomainObject(inputObject);
    }

    private OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject) {
        return converter.convertDomainObjectoToOutputObject(domainObject);
    }
}
