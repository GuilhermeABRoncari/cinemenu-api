package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.AccountDeleteRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserPreferencesRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.UserProfileRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.PreviewMediaResponsePage;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserPreferencesResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserProfileResponseDto;
import br.com.cinemenu.cinemenuapi.domain.entity.user.CineMenuUser;
import br.com.cinemenu.cinemenuapi.domain.repository.UserRepository;
import br.com.cinemenu.cinemenuapi.infra.security.AuthenticationFacade;
import br.com.cinemenu.cinemenuapi.rest.service.CineMenuUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository repository;
    private final AuthenticationFacade authenticationFacade;
    private final CineMenuUserService service;


    @PostMapping("/preference")
    @Operation(
            summary = "Set user preferences.",
            description = " Set the preferences of the current user.",
            responses = {
            @ApiResponse(responseCode = "201", description = "Successfully return.")
    }
    )
    public ResponseEntity<UserPreferencesResponseDto> setUserPreferences(@RequestBody @Valid UserPreferencesRequestDto userPreferencesRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.setUserPreferences(getUser(), userPreferencesRequestDto));
    }

    @GetMapping("/recommendations")
    @Operation(
            summary = "Get recommendations of medias.",
            description = """
                    Get a page of medias from the movie database based in user preferences.
                    By default, the initial index of the page is based on 1 (one).
                    Page number can not be less than 0 (zero) or more than 500 (Five Hundred).
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "page", description = "Page number", required = true)
            }
    )
    public ResponseEntity<PreviewMediaResponsePage> getRecommendationsByUserPreferences(@RequestParam("page") Integer pageNumber) {
        return ResponseEntity.ok(service.getRecommendations(getUser(), pageNumber));
    }

    @GetMapping("/details")
    @Operation(
            summary = "Get the current user details.",
            description = "Return the user profile from the current user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            }
    )
    public ResponseEntity<UserProfileResponseDto> getUserProfile() {
        return ResponseEntity.ok(service.getUserProfile(getUser()));
    }

    @GetMapping("/details_bio")
    @Operation(
            summary = "Get user details profile by ID.",
            description = "Return a user profile from the provided user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            },
            parameters = {
                    @Parameter(name = "id", description = "User ID.")
            }
    )
    public ResponseEntity<UserProfileResponseDto> getUserProfileById(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(service.getUserProfileById(id));
    }

    @PutMapping("/details_bio")
    @Operation(
            summary = "Edit the current user profile.",
            description = "Change/edit the profile details from the current user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully return.")
            }
    )
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(@RequestBody @Valid UserProfileRequestDto dto) {
        return ResponseEntity.ok(service.updateUserProfile(getUser(), dto));
    }

    @DeleteMapping("/account")
    @Operation(
            summary = "Soft delete the account of the current user.",
            description = "Soft delete the current user account.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully soft deleted.")
            }
    )
    public ResponseEntity<HttpStatus> delete(@RequestBody @Valid AccountDeleteRequestDto dto) {
        service.deleteUserAccount(getUser(), dto);
        return ResponseEntity.noContent().build();
    }

    private CineMenuUser getUser() {
        return (CineMenuUser) repository.findByUsername(authenticationFacade.getAuthentication().getName());
    }
}
