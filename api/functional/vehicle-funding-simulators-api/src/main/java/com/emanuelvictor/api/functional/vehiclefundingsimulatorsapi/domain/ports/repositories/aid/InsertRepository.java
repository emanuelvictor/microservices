package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid;

public interface InsertRepository<T> {

    T insert(T t);
}
