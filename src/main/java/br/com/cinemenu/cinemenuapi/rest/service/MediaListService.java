package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.MediaListRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaListResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import br.com.cinemenu.cinemenuapi.domain.repository.MediaListRepository;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.CineMenuEntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MediaListService {

    private final MediaListRepository repository;

    private static final String LIST_NOT_FOUND_WHIT_USER = "List whit id: %s not found for user: %s";
    private static final String LIST_NOT_FOUND_BY_QUERY = "List not found for query: %s";
    private static final String PUBLIC_LIST_NOT_FOUND_BY_QUERY = "Public lists not found for query: %s";
    private static final String LIST_NOT_FOUND = "List whit id: %s not found";

    @Transactional
    public MediaListResponseDto create(CineMenuUser user, MediaListRequestDto requestDto) {
        MediaList newPreviewMediaList = new MediaList(requestDto, user);
        repository.save(newPreviewMediaList);
        user.getMediaLists().add(newPreviewMediaList);
        return new MediaListResponseDto(newPreviewMediaList);
    }

    public Page<MediaListResponseDto> getAllListsFromUser(CineMenuUser user, Pageable page) {
        List<MediaListResponseDto> list = user.getMediaLists().stream()
                .map(MediaListResponseDto::new)
                .toList();

        return buildPage(list, page);
    }

    public MediaListResponseDto getListById(CineMenuUser user, String listId) {
        var previewMediaList = repository.findById(listId).orElseThrow(() -> new CineMenuEntityNotFoundException(LIST_NOT_FOUND.formatted(listId)));

        if (user.getMediaLists().contains(previewMediaList) || ListVisibility.PUBLIC.equals(previewMediaList.getVisibility()))
            return new MediaListResponseDto(previewMediaList);

        throw new CineMenuEntityNotFoundException(LIST_NOT_FOUND.formatted(listId));
    }

    public Page<MediaListResponseDto> getUserMediaListsBySearch(CineMenuUser user, String query, Pageable page) {
        String sanitizedQuery = query.trim();

        List<MediaList> queryResults = repository.findAllByTitleLikeIgnoreCase("%" + sanitizedQuery + "%");

        var pageList = queryResults.stream()
                .filter(mediaList -> user.getMediaLists().contains(mediaList))
                .map(MediaListResponseDto::new).toList();

        if (pageList.isEmpty()) throw new CineMenuEntityNotFoundException(LIST_NOT_FOUND_BY_QUERY.formatted(query));

        return buildPage(pageList, page);
    }

    public Page<MediaListResponseDto> getPublicMediaListsBySearch(String query, Pageable page) {
        String sanitizedQuery = query.trim();

        List<MediaList> queryResults = repository.findAllByTitleLikeIgnoreCase("%" + sanitizedQuery + "%");
        if (queryResults.isEmpty()) throw new CineMenuEntityNotFoundException(PUBLIC_LIST_NOT_FOUND_BY_QUERY.formatted(query));

        var pageList = queryResults.stream()
                .filter(mediaList -> ListVisibility.PUBLIC.equals(mediaList.getVisibility()))
                .map(MediaListResponseDto::new).toList();

        if (pageList.isEmpty()) throw new CineMenuEntityNotFoundException(PUBLIC_LIST_NOT_FOUND_BY_QUERY.formatted(query));

        return buildPage(pageList, page);
    }

    @Transactional
    public MediaListResponseDto editById(CineMenuUser user, String listId, MediaListRequestDto requestDto) {
        MediaList mediaList = repository.findById(listId).orElseThrow(() -> new CineMenuEntityNotFoundException(LIST_NOT_FOUND_WHIT_USER.formatted(listId, user.getUsername())));

        if (!user.getMediaLists().contains(mediaList))
            throw new CineMenuEntityNotFoundException(LIST_NOT_FOUND_WHIT_USER.formatted(listId, user.getUsername()));

        mediaList.update(requestDto);
        return new MediaListResponseDto(mediaList);
    }

    @Transactional
    public void deleteById(CineMenuUser user, String listId) {
        MediaList mediaList = repository.findById(listId).orElseThrow(() -> new CineMenuEntityNotFoundException(LIST_NOT_FOUND_WHIT_USER.formatted(listId, user.getUsername())));

        if (!user.getMediaLists().contains(mediaList))
            throw new CineMenuEntityNotFoundException(LIST_NOT_FOUND_WHIT_USER.formatted(listId, user.getUsername()));

        user.getMediaLists().remove(mediaList);

        repository.delete(mediaList);
    }

    private Page<MediaListResponseDto> buildPage(List<MediaListResponseDto> list, Pageable page) {
        int start = (int) page.getOffset();
        int end = Math.min((start + page.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), page, list.size());
    }
}
