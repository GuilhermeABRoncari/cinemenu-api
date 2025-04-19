package br.com.cinemenu.cinemenuapi.rest.controller;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.PasswordRecoveryRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.LoginRequestDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.ConfirmPasswordRecoveryResponseDto;
import br.com.cinemenu.cinemenuapi.domain.dto.responsedto.TokenResponseDto;
import br.com.cinemenu.cinemenuapi.rest.service.CineMenuUserService;
import br.com.cinemenu.cinemenuapi.rest.service.EmailService;
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
    private final EmailService emailService;

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

    @PostMapping("/recovery-password")
    @Operation(
            summary = "Recovery password",
            description = """
                    Recovery password using a valid email.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success to recovery password.")
            }
    )
    public ResponseEntity<String> recoveryPassword(@RequestBody @Valid PasswordRecoveryRequestDto request) {
        emailService.sendHashCodeVerificationEmail(request.email());
        return ResponseEntity.ok("Email sent to recovery password.");
    }

    @PostMapping("/recovery-password/confirm")
    @Operation(
            summary = "Confirm recovery password",
            description = """
                    Confirm recovery password using a valid email and hash code.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success to confirm recovery password.")
            }
    )
    public ResponseEntity<ConfirmPasswordRecoveryResponseDto> confirmRecoveryPassword(@RequestBody @Valid PasswordRecoveryRequestDto request) {
        if (!emailService.isHashCodeValid(request.email(), request.recoveryHash())) {
            throw new IllegalArgumentException("Invalid hash code.");
        }

        if (request.newPassword() == null || request.newPassword().isEmpty()) {
            return ResponseEntity.ok(emailService.createResponse("Hash code is valid, but new password is required.",
                    request.email(), null));
        }

        signupService.passwordValidation(request.newPassword(), request.newPassword());
        cineMenuUserService.updatePassword(request.email(), request.newPassword());
        String newAccessToken = cineMenuUserService.login(new LoginRequestDto(request.email(), request.newPassword())).token();

        return ResponseEntity.ok(emailService.createResponse("Password updated successfully.", request.email(),
                newAccessToken));
    }
}
