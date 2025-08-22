package com.security.identity;

import java.util.UUID;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.security.identity.entities.UserEntity;
import com.security.identity.enums.RecordStatusEnum;
import com.security.identity.repositories.UserRepository;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserRepository userRepository;

    public CustomOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOAuth2UserService called!");

        OidcUser oidcUser = new OidcUserService().loadUser(userRequest);

        String name = oidcUser.getAttribute("name");
        String email = oidcUser.getAttribute("email");

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setStatus(RecordStatusEnum.ACTIVE);
                    newUser.setDid("did:blockID:" + UUID.randomUUID());
                    return userRepository.save(newUser);
                });

        return new CustomUserPrincipal(user.getName(), user.getEmail(), oidcUser.getAuthorities(), null, oidcUser);
    }

}
