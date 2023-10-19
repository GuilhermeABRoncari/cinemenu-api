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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final AuthenticationFacade authenticationFacade;
    private final CineMenuUserService service;


    @PostMapping("/preference")
    public ResponseEntity<UserPreferencesResponseDto> setUserPreferences(@RequestBody @Valid UserPreferencesRequestDto userPreferencesRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.setUserPreferences(getUser(), userPreferencesRequestDto));
    }

    @GetMapping("/recommendations")
    public ResponseEntity<PreviewMediaResponsePage> getRecommendationsByUserPreferences(@RequestParam("page") Integer pageNumber) {
        return ResponseEntity.ok(service.getRecommendations(getUser(), pageNumber));
    }

    @GetMapping("/details")
    public ResponseEntity<UserProfileResponseDto> getUserProfile() {
        return ResponseEntity.ok(service.getUserProfile(getUser()));
    }

    @GetMapping("/details_bio")
    public ResponseEntity<UserProfileResponseDto> getUserProfileById(@RequestParam(name = "id") String id) {
        return ResponseEntity.ok(service.getUserProfileById(id));
    }

    @PutMapping("/details_bio")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(@RequestBody @Valid UserProfileRequestDto dto) {
        return ResponseEntity.ok(service.updateUserProfile(getUser(), dto));
    }

    @DeleteMapping("/account")
    public ResponseEntity<HttpStatus> delete(@RequestBody @Valid AccountDeleteRequestDto dto) {
        service.deleteUserAccount(getUser(), dto);
        return ResponseEntity.noContent().build();
    }

    private CineMenuUser getUser() {
        return (CineMenuUser) repository.findByUsername(authenticationFacade.getAuthentication().getName());
    }
}
