package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.UserResponseDto;
import br.com.cinemenu.cinemenuapi.rest.service.CineMenuUserService;
import br.com.cinemenu.cinemenuapi.rest.service.SignupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CineMenuAuthenticationController {

    private CineMenuUserService cineMenuUserService;
    private SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid CineMenuUserRequestDto userDto) {
        signupService.checkSignValidations(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cineMenuUserService.sign(userDto));
    }

}
