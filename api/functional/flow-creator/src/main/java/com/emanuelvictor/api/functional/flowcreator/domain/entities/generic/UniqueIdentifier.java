package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

public class UniqueIdentifier<ID> implements IUniqueIdentifier<ID> {
    private ID id;

    public UniqueIdentifier() {
    }

    public UniqueIdentifier(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
