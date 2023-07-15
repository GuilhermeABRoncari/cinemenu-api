package br.com.cinemenu.cinemenuapi.rest.service;

import br.com.cinemenu.cinemenuapi.domain.dto.requestdto.CineMenuUserRequestDto;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class SignupService {

    private static final String INVALID_PASSWORD = "The password must contain at least one uppercase letter, one number, one special character, and be between 8 and 32 characters";
    private static final String INVALID_NAME_LENGTH = "Complete name can not be length then 80 characters";
    private static final String INVALID_EMAIL_LENGTH = "Email can not be length then 80 characters";
    private static final String INVALID_USERNAME_LENGTH = "Username can not be length then 16 characters";
    private static final String NOT_SAME_PASSWORD = "The entered passwords must be the same";
    private static final String ONLY_LETTER_OR_NUMBER = "Username can have only letters or numbers";
    private static final String ONLY_LETTER = "Complete name can have only letters";
    private static final int MAX_NAME_LENGTH = 80;
    private static final int MAX_EMAIL_LENGTH = 80;
    private static final int MAX_USERNAME_LENGTH = 16;

    public void checkSignValidations(CineMenuUserRequestDto userDto) {
        checkUsernameSanitization(userDto.username());
        checkCompleteNameSanitization(userDto.name());
        checkEmailLength(userDto.email());
        passwordValidation(userDto.password(), userDto.confirmationPassword());
    }

    void passwordValidation(String password, String confirmationPassword) {
        if (!password.equals(confirmationPassword)) {
            throw new IllegalArgumentException(NOT_SAME_PASSWORD);
        }

        if (password.length() < 8 || password.length() > 32) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }

        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (!hasUppercase || !hasNumber || !hasSpecialChar) {
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }
    }

    void checkUsernameSanitization(String username) {
        if (username.toCharArray().length > MAX_USERNAME_LENGTH) throw new IllegalArgumentException(INVALID_USERNAME_LENGTH);
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) throw new IllegalArgumentException(ONLY_LETTER_OR_NUMBER);
        }
    }

    void checkCompleteNameSanitization(String name) {
        String pattern = "^[a-zA-Z ']+$";

        if (!Pattern.matches(pattern, name)) throw new IllegalArgumentException(ONLY_LETTER);
        if (name.toCharArray().length > MAX_NAME_LENGTH) throw new IllegalArgumentException(INVALID_NAME_LENGTH);
    }

    void checkEmailLength(String email) {
        if (email.toCharArray().length > MAX_EMAIL_LENGTH) throw new IllegalArgumentException(INVALID_EMAIL_LENGTH);
    }
}
