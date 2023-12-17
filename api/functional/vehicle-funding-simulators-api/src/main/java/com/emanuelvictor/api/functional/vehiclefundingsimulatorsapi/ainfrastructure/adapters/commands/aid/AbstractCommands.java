package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.commands.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.ainfrastructure.adapters.InstanciesConverter;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.capplication.ports.commands.aid.Commands;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.DeleteRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractCommands<DomainObject, InputObject, OutputObject> implements Commands<InputObject, OutputObject> {

    @Autowired(required = false)
    private CreateRepository<DomainObject> insertRepository;

    @Autowired(required = false)
    private UpdateRepository<DomainObject> updateRepository;

    @Autowired(required = false)
    private DeleteRepository<DomainObject> deleteRepository;

    @Override
    public OutputObject create(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        return convertDomainObjectoToOutputObject(insertRepository.create(domainObject));
    }

    @Override
    public OutputObject update(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        return convertDomainObjectoToOutputObject(updateRepository.update(domainObject));
    }

    @Override
    public void delete(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        deleteRepository.delete(domainObject);
    }

    protected DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return InstanciesConverter.tryConvertInstances(getDomainObjectClass(), inputObject);
    }


    protected OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject) {
        return InstanciesConverter.tryConvertInstances(getOutputObjectClass(), domainObject);
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
