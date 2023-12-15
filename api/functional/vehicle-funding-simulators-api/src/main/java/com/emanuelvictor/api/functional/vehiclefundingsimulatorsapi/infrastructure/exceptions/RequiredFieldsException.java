package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.infrastructure.exceptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RequiredFieldsException extends IllegalArgumentException {

    static final long serialVersionUID = -7034897190745766121L;
    private List<String> errors = new ArrayList<>();

    public RequiredFieldsException() {
        super();
    }

    private RequiredFieldsException(final String message, final List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public RequiredFieldsException whenNullOrBlank(final String field, final String message) {
        if (field == null || field.trim().isEmpty()) {
            this.errors.add(message);
        }
        return this;
    }

    public RequiredFieldsException whenNull(final Object field, final String message) {
        if (field == null) {
            this.errors.add(message);
        }
        return this;
    }

    public RequiredFieldsException whenNullOrEqualsToZero(final Long field, final String message) {
        if (field == null || field == 0) {
            this.errors.add(message);
        }
        return this;
    }

    public RequiredFieldsException whenNullOrEqualsToZero(final BigDecimal field, final String message) {
        if (field == null || field.equals(BigDecimal.ZERO)) {
            this.errors.add(message);
        }
        return this;
    }

    public void thenThrows() {
        if (!this.errors.isEmpty()) {
            String message = this.buildMessage();
            throw new RequiredFieldsException(message, this.errors);
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
