package com.emanuelvictor.api.functional.accessmanager.application.resource;

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

    private static final String APP_IDENTIFIER = "access-manager";

    /**
     * Grupos de acesso
     */
    private static final String GROUP_MAPPING = "groups";
    static final String GROUP_MAPPING_RESOURCE = SEPARATOR + GROUP_MAPPING;

    private static final String BASE_GROUP_ROLE = ROOT + SEPARATOR + APP_IDENTIFIER + SEPARATOR + GROUP_MAPPING;
    static final String GROUP_POST_ROLE = BASE_GROUP_ROLE + SEPARATOR + "post";
    static final String GROUP_PUT_ROLE = BASE_GROUP_ROLE + SEPARATOR + "put";
    static final String GROUP_GET_ROLE = BASE_GROUP_ROLE + SEPARATOR + "get";
    static final String GROUP_DELETE_ROLE = BASE_GROUP_ROLE + SEPARATOR + "delete";

    /**
     * Usuários
     */
    private static final String USER_MAPPING = "users";
    static final String USER_MAPPING_RESOURCE = SEPARATOR + USER_MAPPING;

    private static final String BASE_USER_ROLE = ROOT + SEPARATOR + APP_IDENTIFIER + SEPARATOR + USER_MAPPING;
    static final String USER_POST_ROLE = BASE_USER_ROLE + SEPARATOR + "post";
    static final String USER_PUT_ROLE = BASE_USER_ROLE + SEPARATOR + "put";
    static final String USER_GET_ROLE = BASE_USER_ROLE + SEPARATOR + "get";
    static final String USER_PUT_ACTIVATE_ROLE = USER_PUT_ROLE + SEPARATOR + "activate";
    static final String USER_PUT_CHANGE_PASSWORD_ROLE = USER_PUT_ROLE + SEPARATOR + "change-password";

    /**
     * Aplicações
     */
    private static final String APPLICATION_MAPPING = "applications";
    static final String APPLICATION_MAPPING_RESOURCE = SEPARATOR + APPLICATION_MAPPING;

    private static final String BASE_APPLICATION_ROLE = ROOT + SEPARATOR + APP_IDENTIFIER + SEPARATOR + APPLICATION_MAPPING;
    static final String APPLICATION_POST_ROLE = BASE_APPLICATION_ROLE + SEPARATOR + "post";
    static final String APPLICATION_PUT_ROLE = BASE_APPLICATION_ROLE + SEPARATOR + "put";
    static final String APPLICATION_GET_ROLE = BASE_APPLICATION_ROLE + SEPARATOR + "get";
    static final String APPLICATION_PUT_ACTIVATE_ROLE = APPLICATION_PUT_ROLE + SEPARATOR + "activate";
    static final String APPLICATION_PUT_CHANGE_PASSWORD_ROLE = APPLICATION_PUT_ROLE + SEPARATOR + "change-password";
    
    
    /**
     * Permissões
     */
    private static final String PERMISSION_MAPPING = "permissions";
    static final String PERMISSION_MAPPING_RESOURCE = SEPARATOR + PERMISSION_MAPPING;

    private static final String BASE_PERMISSION_ROLE = ROOT + SEPARATOR + APP_IDENTIFIER + SEPARATOR + PERMISSION_MAPPING;
    static final String PERMISSION_GET_ROLE = BASE_PERMISSION_ROLE + SEPARATOR + "get";

}
