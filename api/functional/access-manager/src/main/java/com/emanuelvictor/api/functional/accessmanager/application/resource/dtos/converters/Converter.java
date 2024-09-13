package com.emanuelvictor.api.functional.accessmanager.application.resource.dtos.converters;

import org.springframework.data.domain.Page;

import java.util.Map;

public abstract class Converter<D, O> extends io.github.emanuelvictor.commons.converter.Converter<D, O> {

    public Converter() {
    }

    public Converter(Map<Object, Object> pool) {
        super(pool);
    }

    public Page<D> convert(final Page<O> origins) {
        if (origins == null) return null;
        return origins.map(this::convert);
    }

}
