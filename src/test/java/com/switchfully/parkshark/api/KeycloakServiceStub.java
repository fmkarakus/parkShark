package com.switchfully.parkshark.api;

import com.switchfully.parkshark.security.KeycloakService;
import com.switchfully.parkshark.security.KeycloakUserDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class KeycloakServiceStub implements KeycloakService {
    @Override
    public void addUser(KeycloakUserDTO keycloakUserDTO) {

    }
}
