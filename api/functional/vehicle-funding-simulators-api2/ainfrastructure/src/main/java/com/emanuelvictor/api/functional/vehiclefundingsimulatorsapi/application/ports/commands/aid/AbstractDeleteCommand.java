package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.commands.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.aid.converters.InstanciesConverterWithGson;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.DeleteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractDeleteCommand<DomainObject, InputObject> implements DeleteCommand<InputObject> {

    @Autowired(required = false)
    private DeleteRepository<DomainObject> deleteRepository;

    @Override
    public void execute(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        deleteRepository.delete(domainObject);
    }

    protected DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return InstanciesConverterWithGson.tryConvertInstances(getDomainObjectClass(), inputObject);
    }

    private Class<DomainObject> getDomainObjectClass() {
        return reflectClassType(0);
    }

    @SuppressWarnings("unchecked")
    private Class reflectClassType(int indexOfGenericClass) {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[indexOfGenericClass]);
    }
}
