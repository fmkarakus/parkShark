package com.switchfully.parkshark.security;

public record KeycloakUserDTO (String userName, String password, Role role){
}
