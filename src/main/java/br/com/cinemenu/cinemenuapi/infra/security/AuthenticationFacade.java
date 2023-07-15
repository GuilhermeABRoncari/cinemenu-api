package br.com.cinemenu.cinemenuapi.infra.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}
