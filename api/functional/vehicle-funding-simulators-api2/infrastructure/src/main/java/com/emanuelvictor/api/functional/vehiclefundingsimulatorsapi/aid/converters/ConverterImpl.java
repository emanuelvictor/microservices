package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.aid.converters;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.commands.aid.Converter;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;

@Component
public class ConverterImpl<DomainObject, InputObject, OutputObject> implements Converter<DomainObject, InputObject, OutputObject> {

    @Override
    public DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return InstanciesConverterWithGson.tryConvertInstances(getDomainObjectClass(), inputObject);
    }

    @Override
    public OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject) {
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
