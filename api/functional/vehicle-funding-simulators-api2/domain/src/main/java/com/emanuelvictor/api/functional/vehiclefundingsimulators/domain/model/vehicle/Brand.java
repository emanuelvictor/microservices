package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.exceptions.BusinessException;

public record Brand(String name) {

    static final String INVALID_BRAND_NAME_MESSAGE = "Invalid brand name!";

    public Brand {
        new BusinessException()
                .whenNullOrBlank(name, INVALID_BRAND_NAME_MESSAGE)
                .thenThrows();
    }

}
