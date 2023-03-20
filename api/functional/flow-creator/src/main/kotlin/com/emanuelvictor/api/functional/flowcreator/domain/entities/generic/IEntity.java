package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

public interface IEntity<ID> {

    UniqueIdentifierJava<ID> getIdentifier();

    void setIdentifier(final UniqueIdentifierJava<ID> id);
}
