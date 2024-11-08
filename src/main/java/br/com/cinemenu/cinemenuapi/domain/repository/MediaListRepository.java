package br.com.cinemenu.cinemenuapi.domain.repository;

import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaListRepository extends JpaRepository<MediaList, String> {
    List<MediaList> findAllByTitleLikeIgnoreCase(String query);

    @Query(value = "SELECT m FROM MediaList m WHERE m.visibility = 'PUBLIC' AND m.user = :user")
    List<MediaList> getAllPublicListsFromUser(@Param("user") CineMenuUser user);
}
