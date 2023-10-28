package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.MediaDetailsResultResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.enumeration.MediaType;
import br.com.cinemenu.cinemenuapi.rest.service.PreviewMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinemenu")
@AllArgsConstructor
public class PreviewMediaController {

    private final PreviewMediaService service;

    @GetMapping("/search")
    @Operation(
            summary = "Search for medias.",
            description = """
                    Search for medias using the provide query.
                    By default, the initial index of the page is based on 1 (one).
                    Page number can not be less than 0 (zero) or more than 500 (Five Hundred).
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "q", description = "Search query.", required = true),
                    @Parameter(name = "page", description = "Page number.", required = true)
            }
    )
    public ResponseEntity<PreviewMediaResponsePage> searchMedia(@RequestParam("q") String search, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getSearchResponse(search, page));
    }

    @GetMapping("/genre")
    @Operation(
            summary = "Search for medias by genre.",
            description = """
                    Search for medias using the provide genre IDs.
                    By default, the initial index of the page is based on 1 (one).
                    Page number can not be less than 0 (zero) or more than 500 (Five Hundred).
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "List of CineMenu Genres IDs.", required = true),
                    @Parameter(name = "page", description = "Page number.", required = true)
            }
    )
    public ResponseEntity<PreviewMediaResponsePage> mediaByGenre(@RequestParam("id") List<Integer> genreId, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getGenreResponse(genreId, page));
    }

    @GetMapping("/popular_people")
    @Operation(
            summary = "Get medias from popular people.",
            description = """
                    Search for medias from popular people.
                    By default, the initial index of the page is based on 1 (one).
                    Page number can not be less than 0 (zero) or more than 500 (Five Hundred).
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "page", description = "Page number.", required = true)
            }
    )
    public ResponseEntity<PreviewMediaResponsePage> popularPeopleList(@RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getPopularPeopleList(page));
    }

    @GetMapping("/movies_by_actor")
    @Operation(
            summary = "Get movies from person ID.",
            description = "Search for movies using the provide person ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "Person ID.", required = true)
            }
    )
    public ResponseEntity<PreviewMediaResponsePage> movieListByActorId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(service.getMovieListByActor(id));
    }

    @GetMapping("/series_by_actor")
    @Operation(
            summary = "Get tv shows from person ID.",
            description = """
                    Search for tv shows using the provide person ID.
                    By default, the initial index of the page is based on 1 (one).
                    Page number can not be less than 0 (zero) or more than 500 (Five Hundred).
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "Person ID.", required = true)
            }
    )
    public ResponseEntity<PreviewMediaResponsePage> seriesListByActorId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(service.getSeriesListByActor(id));
    }

    @GetMapping("/similar_by_media")
    @Operation(
            summary = "Get similar medias.",
            description = "Return a page of similar medias based in the provided media type and ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID from the movie database", required = true),
                    @Parameter(name = "media", description = "Media Type = TV or MOVIE.", required = true),
                    @Parameter(name = "page", description = "Page number", required = true)
            }
    )
    public ResponseEntity<PreviewMediaResponsePage> similarByIdAndMedia(
            @RequestParam("id") Long id, @RequestParam("media") MediaType media, @RequestParam("page") Integer page) {
        return ResponseEntity.ok(service.getSimilarByIdAndMedia(id, media, page));
    }

    @GetMapping("/media_detail")
    @Operation(
            summary = "Get media details.",
            description = """
                    Return media details contains all credentials for this provided media.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "ID from the movie database", required = true),
                    @Parameter(name = "media", description = "Media Type = TV or MOVIE.", required = true)
            }
    )
    public ResponseEntity<MediaDetailsResultResponseDto> mediaDetail(@RequestParam("media") MediaType media, @RequestParam("id") Long id) {
        return ResponseEntity.ok(service.getMediaDetail(media, id));
    }
}
