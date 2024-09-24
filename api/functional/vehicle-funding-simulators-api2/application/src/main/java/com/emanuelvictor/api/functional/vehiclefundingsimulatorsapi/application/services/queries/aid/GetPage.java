package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.application.services.queries.aid;

import org.springframework.data.domain.Page;

public interface GetPage<T, Filter extends AbstractFilter> {

    Page<T> execute(final Filter filter);
}
