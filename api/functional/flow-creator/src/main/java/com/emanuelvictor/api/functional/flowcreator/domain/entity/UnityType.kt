package com.emanuelvictor.api.functional.flowcreator.domain.entities;

/**
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 2.0.0, 05/06/2015
 */
public enum UnityType {

    JURIDIC_PERSON("JURIDIC_PERSON"),
    PHYSIC_PERSON("PHYSIC_PERSON"),
    NORMAL("NORMAL");

    private final String unityType;

    /*-------------------------------------------------------------------
     *								CONSTRUCTORS
     *-------------------------------------------------------------------*/

    /**
     *
     */
    UnityType(final String unityType) {
        this.unityType = unityType;
    }

    /**
     * @return the grantType
     */
    public String getValue() {
        return unityType;
    }
}
