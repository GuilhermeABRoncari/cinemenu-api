package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserMediaUpdateMethodRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserMediaResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.entity.MediaList;
import br.com.cinemenu.cinemenuapi.domain.entity.UserMedia;
import br.com.cinemenu.cinemenuapi.domain.enumeration.ListVisibility;
import br.com.cinemenu.cinemenuapi.domain.repository.MediaListRepository;
import br.com.cinemenu.cinemenuapi.domain.repository.UserMediaRepository;
import br.com.cinemenu.cinemenuapi.infra.exceptionhandler.exception.CineMenuEntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserMediaService {

    private final UserMediaRepository userMediaRepository;
    private final MediaListRepository mediaListRepository;
    private static final String LIST_NOT_FOUND_WHIT_USER = "List whit id: %s not found for user: %s";
    private static final String MEDIA_NOT_FOUND_WHIT_USER = "Media whit id: %s not found for user: %s";
    private static final String INVALID_USER_MEDIA_REQUEST_DTO = "Fields 'id_tmdb' and 'media_type' is required";

    @Transactional
    public List<UserMediaResponseDto> createNewUserMediaAndAddToMediaList(CineMenuUser user, String mediaListId, UserMediaRequestDto requestDtoList) {
        MediaList mediaList;
        try {
            mediaList = user.getMediaListById(mediaListId);
        } catch (NoSuchElementException ex) {
            throw new CineMenuEntityNotFoundException(LIST_NOT_FOUND_WHIT_USER.formatted(mediaListId, user.getUsername()));
        }

        requestDtoList.medias().forEach(userMediaRequestDto -> {
            if (userMediaRequestDto.idTmdb() == null || userMediaRequestDto.mediaType() == null)
                throw new IllegalArgumentException(INVALID_USER_MEDIA_REQUEST_DTO);
        });

        List<UserMedia> userMedia = userMediaRepository.saveAll(requestDtoList.medias().stream().map(UserMedia::new).toList());
        mediaList.getUserMedias().addAll(userMedia);

        return List.copyOf(userMedia.stream().map(UserMediaResponseDto::new).toList());
    }

    @Transactional
    public UserMediaResponseDto editMediaByIdFromUserMediaList(CineMenuUser user, String mediaId, UserMediaUpdateMethodRequestDto updateRequestDto) {
        var media = userMediaRepository.findById(mediaId).orElseThrow(() -> new CineMenuEntityNotFoundException(MEDIA_NOT_FOUND_WHIT_USER.formatted(mediaId, user.getUsername())));

        if (user.getMediaLists().stream().noneMatch(mediaList -> mediaList.getUserMedias().contains(media)))
            throw new CineMenuEntityNotFoundException(MEDIA_NOT_FOUND_WHIT_USER.formatted(mediaId, user.getUsername()));

        media.update(updateRequestDto);
        return new UserMediaResponseDto(media);
    }

    @Transactional
    public void deleteById(CineMenuUser user, String mediaId) {
        var media = userMediaRepository.findById(mediaId).orElseThrow(() -> new CineMenuEntityNotFoundException(MEDIA_NOT_FOUND_WHIT_USER.formatted(mediaId, user.getUsername())));

        MediaList result = user.getMediaLists().stream().filter(mediaList -> mediaList.getUserMedias().contains(media)).findAny().orElseThrow(() -> new CineMenuEntityNotFoundException(MEDIA_NOT_FOUND_WHIT_USER.formatted(mediaId, user.getUsername())));

        result.getUserMedias().remove(media);
        userMediaRepository.delete(media);
    }

    public Page<UserMediaResponseDto> getUserMediaPagesFromListId(CineMenuUser user, String mediaListId, Pageable page) {
        var mediaList = mediaListRepository.findById(mediaListId).orElseThrow(() -> new CineMenuEntityNotFoundException(LIST_NOT_FOUND_WHIT_USER.formatted(mediaListId, user.getUsername())));

        if (user.getMediaLists().contains(mediaList) || ListVisibility.PUBLIC.equals(mediaList.getVisibility())) {
            List<UserMediaResponseDto> responseList = new ArrayList<>(mediaList.getUserMedias().stream().map(UserMediaResponseDto::new).toList());

            int start = (int) page.getOffset();
            int end = Math.min((start + page.getPageSize()), responseList.size());
            return new PageImpl<>(responseList.subList(start, end), page, responseList.size());
        }

        throw new CineMenuEntityNotFoundException(LIST_NOT_FOUND_WHIT_USER.formatted(mediaListId, user.getUsername()));
    }
}
