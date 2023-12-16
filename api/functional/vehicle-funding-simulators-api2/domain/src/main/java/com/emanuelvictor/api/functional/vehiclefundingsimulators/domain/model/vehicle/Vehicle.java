package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.vehicle;

import com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.exceptions.BusinessException;

public record Vehicle(String plateNumber, Model model) {

    static final String INVALID_PLATE_NUMBER_MESSAGE = "Invalid plate number!";
    static final String INVALID_MODEL_MESSAGE = "Invalid model!";

    public Vehicle {
        new BusinessException()
                .whenNullOrBlank(plateNumber, INVALID_PLATE_NUMBER_MESSAGE)
                .whenNull(model, INVALID_MODEL_MESSAGE)
                .thenThrows();
    }

    public String getModelName() {
        return model.name();
    }

    public String getBrandName() {
        return model.getBrandName();
    }
}
