package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

import java.io.Serializable;

public class UniqueIdentifierJava<ID> implements Serializable {
    private ID id;

    public UniqueIdentifierJava() {
    }

    public UniqueIdentifierJava(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
