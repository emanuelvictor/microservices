package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid;

public interface Converter<DomainObject, InputObject, OutputObject> {

    DomainObject convertInputObjectToDomainObject(InputObject inputObject);

    OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject);

}
