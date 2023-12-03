package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.exceptions.RequiredFieldsException;

public record Brand(String name) {

    static final String INVALID_BRAND_NAME_MESSAGE = "Invalid brand name!";

    public Brand {
        new RequiredFieldsException()
                .whenNullOrBlank(name, INVALID_BRAND_NAME_MESSAGE)
                .thenThrows();
    }

}
