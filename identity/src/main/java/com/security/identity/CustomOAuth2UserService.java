package com.security.identity;

import java.util.UUID;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.security.identity.entities.UserEntity;
import com.security.identity.enums.RecordStatusEnum;
import com.security.identity.repositories.UserRepository;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("CustomOAuth2UserService called!");

        OAuth2User oAuthUser = new DefaultOAuth2UserService().loadUser(userRequest);

        String name = oAuthUser.getAttribute("name");
        String email = oAuthUser.getAttribute("email");

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    UserEntity newUser = new UserEntity();
                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setStatus(RecordStatusEnum.ACTIVE);
                    newUser.setDid("did:blockID:" + UUID.randomUUID());
                    return userRepository.save(newUser);
                });

        return new CustomUserPrincipal(user, oAuthUser.getAttributes());
    }

}
