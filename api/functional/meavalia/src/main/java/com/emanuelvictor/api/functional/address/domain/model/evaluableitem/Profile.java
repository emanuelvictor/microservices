package com.emanuelvictor.api.functional.address.domain.model.evaluableitem;

public enum Profile {

    ADMINISTRATOR(0), // 0
    OPERATOR(1), // 1
    ATTENDANT(2), // 2
    ANONYMOUS(3), // 3
    ROOT(4), // 4
    DEVICE(5); // 5

    public static final String ADMINISTRADOR_VALUE = "ADMINISTRATOR";
    public static final String OPERADOR_VALUE = "OPERATOR";
    public static final String ATENDENTE_VALUE = "ATTENDANT";
    public static final String ANONYMOUS_VALUE = "ANONYMOUS";
    public static final String ROOT_VALUE = "ROOT";
    public static final String DISPOSITIVO_VALUE = "DEVICE";

    /**
     *
     */
    public final int profile;

    /**
     * @param profile int
     */
    Profile(final int profile) {
        this.profile = profile;
    }

}
