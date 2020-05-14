package com.cloud.oauth.adauthenication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;
import java.util.Objects;

/**
 * ad域认证token
 */
public class AdAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;

    /**
     * 未认证时，内容为手机号
     *
     * @param
     */
    public AdAuthenticationToken(String account) {
        super(null);
        this.principal = account;
        setAuthenticated(false);
    }

    /**
     * 认证成功后，其中为用户信息
     *
     * @param principal
     * @param authorities
     */
    public AdAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    public AdAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal) {
        super(authorities);
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AdAuthenticationToken that = (AdAuthenticationToken) o;
        return Objects.equals(principal, that.principal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), principal);
    }

}