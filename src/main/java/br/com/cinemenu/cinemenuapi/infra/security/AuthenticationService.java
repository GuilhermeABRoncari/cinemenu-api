package br.com.cinemenu.cinemenuapi.infra.security;

import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Generated
public class AuthenticationService implements UserDetailsService {

    private final UserRepository repository;
    private static final String USER_NOT_FOUND = "User not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = repository.findByUsername(username);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
    }
}
