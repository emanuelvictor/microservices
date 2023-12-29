package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.InstanciesConverterWithGson;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.UpdateCommand;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractUpdateCommand<DomainObject, InputObject, OutputObject> implements UpdateCommand<InputObject, OutputObject> {

    @Autowired(required = false)
    private UpdateRepository<DomainObject> updateRepository;

    @Override
    public OutputObject execute(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        return convertDomainObjectoToOutputObject(updateRepository.update(domainObject));
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
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[indexOfGenericClass]);
    }
}
