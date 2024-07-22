package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.impl;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.Converter;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.UpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUpdateCommandImpl<DomainObject, InputObject, OutputObject> implements UpdateCommand<InputObject, OutputObject> {

    @Autowired(required = false) // TODO substituir pelo construtor
    private UpdateRepository<DomainObject> updateRepository;

    @Autowired
    private Converter<DomainObject, InputObject, OutputObject> converter;

    @Override
    public OutputObject execute(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        return convertDomainObjectoToOutputObject(updateRepository.update(domainObject));
    }

    private DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return converter.convertInputObjectToDomainObject(inputObject);
    }

    private OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject) {
        return converter.convertDomainObjectoToOutputObject(domainObject);
    }

}
