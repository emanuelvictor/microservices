package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

public interface IEntity<ID> {

    UniqueIdentifier<ID> getId();

    void setId(final UniqueIdentifier<ID> id);
}
