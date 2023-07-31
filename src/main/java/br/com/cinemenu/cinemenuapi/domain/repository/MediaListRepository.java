package br.com.cinemenu.cinemenuapi.domain.repository;

import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaListRepository extends JpaRepository<MediaList, String> {
    List<MediaList> findAllByTitleLikeIgnoreCase(String query);
}
