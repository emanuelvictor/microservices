package com.emanuelvictor.api.functional.assessment.application.resource;

/**
 * Armazena as permissões do sistema
 *
 * @author Emanuel Victor
 * @version 1.0.0
 * @since 1.0.0, 10/09/2019
 */
public class Roles {

    private static final String SEPARATOR = "/";

    private static final String ROOT = "root";

    private static final String APP_IDENTIFIER = "assessment";

    /**
     * Usuários
     */
    private static final String UNITY_MAPPING = "unities";
    static final String UNITY_MAPPING_RESOURCE = SEPARATOR + UNITY_MAPPING;

    private static final String BASE_UNITY_ROLE = ROOT + SEPARATOR + APP_IDENTIFIER + SEPARATOR + UNITY_MAPPING;
    static final String UNITY_POST_ROLE = BASE_UNITY_ROLE + SEPARATOR + "post";
    static final String UNITY_PUT_ROLE = BASE_UNITY_ROLE + SEPARATOR + "put";
    static final String UNITY_GET_ROLE = BASE_UNITY_ROLE + SEPARATOR + "get";

}
