package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.exceptions.BusinessException;

public record Model(String name, Brand brand) {

    static final String INVALID_MODEL_NAME_MESSAGE = "Invalid model name!";
    static final String INVALID_BRAND_MESSAGE = "Invalid brand!";

    public Model {
        new BusinessException()
                .whenNullOrBlank(name, INVALID_MODEL_NAME_MESSAGE)
                .whenNull(brand, INVALID_BRAND_MESSAGE)
                .thenThrows();
    }

    public String getBrandName() {
        return brand.name();
    }
}
