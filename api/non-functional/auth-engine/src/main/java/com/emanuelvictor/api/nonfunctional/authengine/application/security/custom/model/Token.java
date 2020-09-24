package com.emanuelvictor.api.nonfunctional.authengine.application.security.custom.model;

public interface Token {

    public void revoke();

    public void revokeChilds();

    public void revokeParent();

    public void setParent(Token token);

    public boolean isRevoked();
}
