package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.util.UUID;

@MappedSuperclass
public abstract class Entity<ID extends UniqueIdentifier<?>> implements IEntity<String> {

    @EmbeddedId
    private UniqueIdentifier<String> identifier;

    @PrePersist
    void prePersist() {
        if (this.identifier == null) {
            setIdentifier(new UniqueIdentifier<>(UUID.randomUUID().toString().replace("-", "")));
        }
        if (this.identifier.getId() == null)
            this.identifier.setId(UUID.randomUUID().toString().replace("-", ""));
    }

    public IUniqueIdentifier<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(IUniqueIdentifier<String> id) {
        this.identifier = (UniqueIdentifier<String>) id;
    }

}
