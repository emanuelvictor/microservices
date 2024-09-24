package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid;

public interface UpdateRepository<T> {

    T update(final Object id, T t);
}
