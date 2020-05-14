package com.cloud.oauth.adauthenication;

import org.springframework.security.core.AuthenticationException;

public class AdAuthenticationException extends AuthenticationException {

    public AdAuthenticationException(String msg) {
        super(msg);
    }
}
