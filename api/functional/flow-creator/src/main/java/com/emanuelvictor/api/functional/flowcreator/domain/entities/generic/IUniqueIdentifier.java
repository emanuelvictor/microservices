package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

import java.io.Serializable;

public interface IUniqueIdentifier<ID> extends Serializable {

    ID getId();

    void setId(final ID id);
}
