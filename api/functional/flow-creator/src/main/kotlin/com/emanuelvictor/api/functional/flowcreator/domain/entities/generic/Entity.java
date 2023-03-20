package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@MappedSuperclass
public abstract class Entity<ID extends UniqueIdentifierJava<?>> implements IEntity<String> {

    @EmbeddedId
    public UniqueIdentifierJava<String> identifier;

    @PrePersist
    void prePersist() {
        if (this.identifier == null) {
            setIdentifier(new UniqueIdentifierJava<>(UUID.randomUUID().toString().replace("-", "")));
        }
        if (this.identifier.getId() == null)
            this.identifier.setId(UUID.randomUUID().toString().replace("-", ""));
    }

    public UniqueIdentifierJava<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UniqueIdentifierJava<String> id) {
        this.identifier = id;
    }

}
