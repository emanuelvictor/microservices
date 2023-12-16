package com.emanuelvictor.api.functional.vehiclefundingsimulators.domain.domain.model.exceptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BusinessException extends IllegalArgumentException {

    static final long serialVersionUID = -7034897190745766121L;
    private List<String> errors = new ArrayList<>();

    public BusinessException() {
        super();
    }

    private BusinessException(final String message, final List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public BusinessException whenNullOrBlank(final String field, final String message) {
        if (field == null || field.trim().isEmpty()) {
            this.errors.add(message);
        }
        return this;
    }

    public BusinessException whenNull(final Object field, final String message) {
        if (field == null) {
            this.errors.add(message);
        }
        return this;
    }

    public BusinessException whenNullOrEqualsToZero(final Long field, final String message) {
        if (field == null || field == 0) {
            this.errors.add(message);
        }
        return this;
    }

    public BusinessException whenNullOrEqualsToZero(final BigDecimal field, final String message) {
        if (field == null || field.equals(BigDecimal.ZERO)) {
            this.errors.add(message);
        }
        return this;
    }

    public void thenThrows() {
        if (!this.errors.isEmpty()) {
            String message = this.buildMessage();
            throw new BusinessException(message, this.errors);
        }
    }

    private String buildMessage() {
        final StringBuilder message = new StringBuilder();

        for (final String error : this.errors) {
            message.append(error).append("\n");
        }

        return message.toString();
    }

}
