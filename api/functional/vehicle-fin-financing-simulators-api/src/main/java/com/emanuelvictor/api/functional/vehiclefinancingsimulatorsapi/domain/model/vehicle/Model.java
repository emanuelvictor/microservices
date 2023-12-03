package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.infrastructure.exceptions.RequiredFieldsException;

public record Model(String name, Brand brand) {

    static final String INVALID_MODEL_NAME_MESSAGE = "Invalid model name!";
    static final String INVALID_BRAND_MESSAGE = "Invalid brand!";

    public Model {
        new RequiredFieldsException()
                .whenNullOrBlank(name, INVALID_MODEL_NAME_MESSAGE)
                .whenNull(brand, INVALID_BRAND_MESSAGE)
                .thenThrows();
    }

    public String getBrandName() {
        return brand.name();
    }
}
