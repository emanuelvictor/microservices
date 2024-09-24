package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.aid;

import java.util.Optional;

@FunctionalInterface
public interface GetOne<T, Filter> {

    Optional<T> execute(Filter filter);
}
