package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.LoginRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.TokenResponseDto;
import br.com.cinemenu.cinemenuapi.rest.service.CineMenuUserService;
import br.com.cinemenu.cinemenuapi.rest.service.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final CineMenuUserService cineMenuUserService;
    private final SignupService signupService;

    @PostMapping("/signup")
    @Operation(
            summary = "Create user account",
            description = """
            Create a user account using a unique username and email, name, password whose required eight characters containing at least one uppercase letter, one special character, and one number.
            """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success to account creation and login automatically.")
            }
    )
    public ResponseEntity<TokenResponseDto> signup(@RequestBody @Valid CineMenuUserRequestDto userDto) {
        signupService.checkSignValidations(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cineMenuUserService.sign(userDto));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login existing user account",
            description = """
            Login to user account using valid email and password.
            """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success to login and return a valid token.")
            }
    )
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto loginDto) {
        return ResponseEntity.ok(cineMenuUserService.login(loginDto));
    }

}
