package br.com.cinemenu.cinemenuapi.domain.repository;

import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<CineMenuUser, String> {
    UserDetails findByUsername(String nickname);
    CineMenuUser getReferenceByEmail(String email);
    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM cine_menu_user u WHERE u.email = :email AND u.deleted = true")
    boolean checkUserDisableEmail(@Param("email") String email);

    boolean existsByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM cine_menu_user u WHERE u.username = :username AND u.deleted = true")
    boolean checkUserDisableUsername(@Param("username") String username);
}
