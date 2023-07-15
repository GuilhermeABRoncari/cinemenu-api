package br.com.cinemenu.cinemenuapi.domain.repository;

import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<CineMenuUser, String> {
    UserDetails findByUsername(String nickname);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
