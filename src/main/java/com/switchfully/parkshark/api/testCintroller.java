package com.switchfully.parkshark.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import java.util.logging.Logger;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;

@RestController
@RequestMapping(path = "/test")
public class testCintroller {
    @GetMapping(value = "/test-email")
    public void logUsername(Authentication authentication) {
        Logger.getAnonymousLogger().info(authentication.getName());
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('STOP_PARKING')")
    public AccessToken loadUserDetail(KeycloakAuthenticationToken authentication) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();
        //Username, other way
        Logger.getAnonymousLogger().info(authentication.getPrincipal().toString());
        //Email
        Logger.getAnonymousLogger().info(token.getEmail());
        return token;
    }
}
