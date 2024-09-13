package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

public interface IEntity<ID> {

    IUniqueIdentifier<ID> getIdentifier();

    void setIdentifier(final IUniqueIdentifier<ID> id);
}
