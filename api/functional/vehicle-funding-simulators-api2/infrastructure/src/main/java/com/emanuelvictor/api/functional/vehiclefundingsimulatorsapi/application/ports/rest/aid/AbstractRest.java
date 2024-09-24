package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.ports.rest.aid;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid.Converter;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.rests.aid.Rest;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.CreateRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.DeleteRepository;
import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public abstract class AbstractRest<DomainObject, InputObject, OutputObject> implements Rest<InputObject, OutputObject> {

    @Autowired
    private Converter<DomainObject, InputObject, OutputObject> converter;

    @Autowired(required = false)
    private CreateRepository<DomainObject> createRepository;

    @Autowired(required = false)
    private UpdateRepository<DomainObject> updateRepository;

    @Autowired(required = false)
    private DeleteRepository<DomainObject> deleteRepository;

    @PostMapping
    public OutputObject create(InputObject inputObject) {
        final DomainObject domainObject = convertInputObjectToDomainObject(inputObject);
        final DomainObject domainObjectCreated = createRepository.create(domainObject);
        return convertDomainObjectoToOutputObject(domainObjectCreated);
    }

    @PutMapping("{id}")
    public OutputObject update(@PathVariable final Object id, InputObject inputObject) {
        final DomainObject domainObject = convertInputObjectToDomainObject(inputObject);
        final DomainObject domainObjectUpdated = updateRepository.update(id, domainObject);
        return convertDomainObjectoToOutputObject(domainObjectUpdated);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable final Object id) {
        deleteRepository.delete(id);
    }

    private DomainObject convertInputObjectToDomainObject(InputObject inputObject) {
        return converter.convertInputObjectToDomainObject(inputObject);
    }

    private OutputObject convertDomainObjectoToOutputObject(DomainObject domainObject) {
        return converter.convertDomainObjectoToOutputObject(domainObject);
    }

}
