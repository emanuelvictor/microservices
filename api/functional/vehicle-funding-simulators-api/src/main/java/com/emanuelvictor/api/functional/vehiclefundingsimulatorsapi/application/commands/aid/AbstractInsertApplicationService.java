package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.Command;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.InsertRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

import static com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.InstanciesConverter.tryConvertInstances;

public abstract class AbstractInsertApplicationService<DomainObject, InputObject, OutputObject> implements Command<InputObject, OutputObject> {

    @Autowired
    private InsertRepository<DomainObject> insertRepository;

    @Override
    public OutputObject execute(InputObject input) {
        final DomainObject domainObject = convertInputObjectToDomainObject(input);
        return convertDomainObjectoToOutputObject(insertRepository.insert(domainObject));
    }

    protected DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return tryConvertInstances(getDomainObjectClass(), inputObject);
    }


    protected OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject) {
        return tryConvertInstances(getOutputObjectClass(), domainObject);
    }

    protected Class<DomainObject> getDomainObjectClass() {
        return reflectClassType(0);
    }

    protected Class<OutputObject> getOutputObjectClass() {
        return reflectClassType(2);
    }

    @SuppressWarnings("unchecked")
    private Class reflectClassType(int indexOfGenericClass) {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[indexOfGenericClass]);
    }
}
