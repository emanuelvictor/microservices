package com.emanuelvictor.api.functional.flowcreator.domain.entities.generic;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@MappedSuperclass
public abstract class Entity<ID extends UniqueIdentifier<?>> implements IEntity<String> {

    @EmbeddedId
    public UniqueIdentifier<String> id;

    @PrePersist
    void prePersist() {
        if (getId() == null) {
            setId(new UniqueIdentifier<>() {
                @Nullable
                @Override
                public String getId() {
                    return super.getId();
                }
            });
        }
        if (getId().getId() == null)
            getId().setId(UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public UniqueIdentifier<String> getId() {
        return id;
    }

    @Override
    public void setId(UniqueIdentifier<String> id) {
        this.id = id;
    }

}
