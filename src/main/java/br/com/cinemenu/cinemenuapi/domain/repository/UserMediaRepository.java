package br.com.cinemenu.cinemenuapi.domain.repository;

import br.com.cinemenu.cinemenuapi.domain.entity.UserMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMediaRepository extends JpaRepository<UserMedia, String> {

}
