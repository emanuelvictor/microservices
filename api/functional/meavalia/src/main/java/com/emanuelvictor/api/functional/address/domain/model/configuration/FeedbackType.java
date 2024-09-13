package com.emanuelvictor.api.functional.address.domain.model.configuration;

public enum FeedbackType {

    /**
     *
     */
    TEXT(0),

    /**
     *
     */
    EMAIL(1),

    /**
     *
     */
    CPF(2),

    /**
     *
     */
    PHONE(3);

    /**
     *
     */
    public final int typeOfFeedback;

    /**
     * @param typeOfFeedback int
     */
    FeedbackType(final int typeOfFeedback) {
        this.typeOfFeedback = typeOfFeedback;
    }
}
