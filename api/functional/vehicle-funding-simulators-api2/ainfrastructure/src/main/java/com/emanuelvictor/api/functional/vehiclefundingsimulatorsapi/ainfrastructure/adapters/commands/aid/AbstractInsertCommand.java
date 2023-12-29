package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.InstanciesConverterWithGson;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.InsertCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractInsertCommand<DomainObject, InputObject, OutputObject> implements InsertCommand<InputObject, OutputObject> {

    @Autowired(required = false)
    private CreateRepository<DomainObject> insertRepository;

    @Override
    public OutputObject execute(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        return convertDomainObjectoToOutputObject(insertRepository.create(domainObject));
    }

    protected DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return InstanciesConverterWithGson.tryConvertInstances(getDomainObjectClass(), inputObject);
    }

    protected OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject) {
        return InstanciesConverterWithGson.tryConvertInstances(getOutputObjectClass(), domainObject);
    }

    private Class<DomainObject> getDomainObjectClass() {
        return reflectClassType(0);
    }

    private Class<OutputObject> getOutputObjectClass() {
        return reflectClassType(2);
    }

    @SuppressWarnings("unchecked")
    private Class reflectClassType(int indexOfGenericClass) {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[indexOfGenericClass]);
    }
}
