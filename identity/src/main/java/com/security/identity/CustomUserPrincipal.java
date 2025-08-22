package com.security.identity;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomUserPrincipal implements OidcUser {

    private final String id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    // Keep a reference to the delegate (OAuth2User or OidcUser)
    private final OAuth2User oauth2User;
    private final OidcUser oidcUser;

    public CustomUserPrincipal(String id, String email,
            Collection<? extends GrantedAuthority> authorities,
            OAuth2User oauth2User,
            OidcUser oidcUser) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
        this.oauth2User = oauth2User;
        this.oidcUser = oidcUser;
    }

    // Your custom getters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    // Required overrides
    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User != null ? oauth2User.getAttributes() : oidcUser.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return id;
    }

    // OIDC-specific
    @Override
    public Map<String, Object> getClaims() {
        return oidcUser != null ? oidcUser.getClaims() : Map.of();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUser != null ? oidcUser.getUserInfo() : null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcUser != null ? oidcUser.getIdToken() : null;
    }
}
