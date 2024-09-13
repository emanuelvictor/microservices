package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.ports.repositories.aid;

public interface DeleteRepository<T> {

    T delete(T t);
}
