package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

public interface IEntity {


    UniqueIdentifier<String> getId();

    void setId(final UniqueIdentifier<String> id);
}
